package edu.ua.collegeswap.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.TextbookAccessor;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Fragment to display a list of Textbook listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class FragmentTextbooks extends SectionFragment implements View.OnClickListener, SectionFragment.Reloadable {

//    private List<Textbook> textbooks;

    // UI references
    private EditText editTextMinPrice, editTextMaxPrice;
    private Spinner courseSubject, courseNumber;

    // State representation
    protected boolean filterByPrice = false;
    protected float minFilterPrice, maxFilterPrice;
    protected boolean filterByCourse = false;
    protected String filterCourseSubject;
    protected int filterCourseNumber;

    public FragmentTextbooks() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.section_fragment_textbooks, container, false);
        LinearLayout linearLayoutTextbooks = (LinearLayout) view.findViewById(R.id.linearLayoutTextbooks);
        final View.OnClickListener onClickListener = this;

        Button buttonClear = (Button) view.findViewById(R.id.buttonClear);
        Button buttonFilter = (Button) view.findViewById(R.id.buttonFilter);
        buttonClear.setOnClickListener(this);
        buttonFilter.setOnClickListener(this);

        editTextMinPrice = (EditText) view.findViewById(R.id.editTextMinPrice);
        editTextMaxPrice = (EditText) view.findViewById(R.id.editTextMaxPrice);
        courseSubject = (Spinner) view.findViewById(R.id.spinnerSubject);
        courseNumber = (Spinner) view.findViewById(R.id.spinnerNumber);

        // Populate the spinners. Duplicate code from EditTextbookActivity.
        final List<String> courseSubjects = new TextbookAccessor().getCourseSubjects();
        courseSubjects.add(0, "Choose course subject");
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseSubjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> courseNumbers = new ArrayList<>(); // This needs to change when subject is changed
        courseNumbers.add(0, EditTextbookActivity.chooseNumberMessage);
        final ArrayAdapter<String> numberAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseNumbers);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        courseSubject.setAdapter(subjectAdapter);
        courseNumber.setAdapter(numberAdapter);

        // Change the courseNumbers whenever the subject is changed
        courseSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) { // ignore the "choose course subject" item
                    // Give the course number adapter the appropriate numbers for the selected subject.
                    updateCourseNumbers(courseSubjects.get(position), numberAdapter, courseNumber);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // Load the Textbooks
        reloadView(inflater, linearLayoutTextbooks, onClickListener);

        return view;
    }

    private void updateCourseNumbers(String subject, ArrayAdapter<String> numberAdapter, Spinner courseNumber) {
        numberAdapter.clear();
        numberAdapter.add(EditTextbookActivity.chooseNumberMessage);
        numberAdapter.addAll(new TextbookAccessor().getCourseNumbersStrings(subject));
        courseNumber.setSelection(0);
    }

    /**
     * @param inflater              used to inflate each individual textbook layout
     * @param linearLayoutTextbooks holds the textbooks
     * @param onClickListener       for clicking on textbooks
     */
    private void reloadView(final LayoutInflater inflater, final LinearLayout linearLayoutTextbooks, final View.OnClickListener onClickListener) {

        new AsyncTask<Void, Void, List<Textbook>>() {

            @Override
            protected List<Textbook> doInBackground(Void... params) {
                // This makes network calls, so do it in a background thread
                return updateTextbooksFromServer();
            }

            @Override
            protected void onPostExecute(List<Textbook> textbooks) {
                // When the network calls are complete, this runs on the UI thread to show the listings returned from the server.

                linearLayoutTextbooks.removeAllViews(); // Remove the child views, since this can be called again after filtering

                for (Textbook t : textbooks) {
                    // Inflate the individual textbook View and put it inside the parent LinearLayout
                    // (this returns the parent linearLayoutTextbooks, not the individual child View we need)
                    inflater.inflate(R.layout.individual_textbook, linearLayoutTextbooks);

                    // Get a reference to the individual bookmark view we just inflated
                    LinearLayout textbookView = (LinearLayout) linearLayoutTextbooks.getChildAt(
                            linearLayoutTextbooks.getChildCount() - 1);

                    textbookView.setTag(t); // Store the Textbook object as a tag in the View for retrieval when clicked
                    textbookView.setOnClickListener(onClickListener); // allow the user to click on the whole Textbook representation

                    // Find the TextViews and their their text from the Textbook fields

                    TextView title = (TextView) textbookView.findViewById(R.id.textViewTitle);
                    title.setText(t.getTitle());

                    TextView course = (TextView) textbookView.findViewById(R.id.textViewCourse);
                    course.setText(t.getSubjectAndNumber());

                    TextView price = (TextView) textbookView.findViewById(R.id.textViewPrice);
                    price.setText(t.getAskingPriceDollars());
                }
            }
        }.execute();
    }

    private List<Textbook> updateTextbooksFromServer() {
        // Retrieve the list of textbooks from the server
        TextbookAccessor accessor = new TextbookAccessor();

        List<Listing> listings;
        if (filterByPrice) {
            if (filterByCourse) {
                return accessor.get((int) minFilterPrice, (int) maxFilterPrice, filterCourseSubject, filterCourseNumber);
            } else {
                listings = accessor.getByPrice((int) minFilterPrice, (int) maxFilterPrice);
            }
        } else {
            if (filterByCourse) {
                return accessor.getByClass(filterCourseSubject, filterCourseNumber);
            } else {
                listings = accessor.getAll();
            }
        }

        List<Textbook> textbooks = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Textbook) {
                textbooks.add((Textbook) l);
            } else {
                throw new IllegalStateException("Expected a Textbook, but found another class.");
            }
        }

        return textbooks;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClear:
                // Clear the UI filtering inputs
                editTextMinPrice.setText("");
                editTextMaxPrice.setText("");
                courseSubject.setSelection(0);
                courseNumber.setSelection(0);

                // Set the state variables to show all textbooks
                filterByPrice = false;
                filterByCourse = false;

                reloadView();
                break;
            case R.id.buttonFilter:
                // Set the state variables to possibly filter textbooks
                try {
                    minFilterPrice = Float.parseFloat(editTextMinPrice.getText().toString());
                    maxFilterPrice = Float.parseFloat(editTextMaxPrice.getText().toString());

                    filterByPrice = true;
                } catch (Exception e) {
                    filterByPrice = false;
                }

                if (courseSubject.getSelectedItemPosition() != 0) { // Allow filtering only by subject, and optionally number
                    filterCourseSubject = (String) courseSubject.getSelectedItem();
                    filterByCourse = true;
                    try {
                        filterCourseNumber = Integer.parseInt((String) courseNumber.getSelectedItem());
                    } catch (Exception e) {
                        filterCourseNumber = -1; // if the user hasn't selected a course
                    }
                } else {
                    filterByCourse = false;
                }

                reloadView();
                break;
            default:
                if (v.getTag() instanceof Textbook) {
                    // Retrieve the Textbook stored in this View
                    Textbook textbook = (Textbook) v.getTag();

                    callbacks.onListingClicked(textbook);
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_content_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new) {
            // Open the activity to create a new Textbook
            ((MainDrawerActivity) getActivity()).launchNewListingActivity(EditTextbookActivity.class);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void reloadView() {
        reloadView(getActivity().getLayoutInflater(), (LinearLayout) getActivity().findViewById(R.id.linearLayoutTextbooks), this);
    }
}
