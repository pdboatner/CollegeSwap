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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
public class FragmentTextbooks extends SectionFragment implements View.OnClickListener {

//    private List<Textbook> textbooks;

    public FragmentTextbooks() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.section_fragment_textbooks, container, false);

        final View.OnClickListener onClickListener = this;

        new AsyncTask<Void, Void, List<Textbook>>() {

            @Override
            protected List<Textbook> doInBackground(Void... params) {
                // This makes network calls, so do it in a background thread
                return updateTextbooksFromServer();
            }

            @Override
            protected void onPostExecute(List<Textbook> textbooks) {
                // When the network calls are complete, this runs on the UI thread to show the listings returned from the server.

                // Get a reference to the linear layout which will hold the View for each textbook
                LinearLayout linearLayoutTextbooks = (LinearLayout) view.findViewById(R.id.linearLayoutTextbooks);

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

                    TextView title = (TextView) textbookView.findViewById(R.id.AptName);
                    title.setText(t.getTitle());

                    TextView course = (TextView) textbookView.findViewById(R.id.textViewCourse);
                    course.setText(t.getSubjectAndNumber());

                    TextView price = (TextView) textbookView.findViewById(R.id.textPriceLabel);
                    price.setText("$" + t.getAskingPrice());
                }
            }
        }.execute();

        return view;
    }

    private List<Textbook> updateTextbooksFromServer() {
        // Retrieve the list of textbooks from the server
        TextbookAccessor accessor = new TextbookAccessor();
        List<Listing> listings = accessor.getAll();
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
        if (v.getTag() instanceof Textbook) {
            // Retrieve the Textbook stored in this View
            Textbook textbook = (Textbook) v.getTag();

            callbacks.onListingClicked(textbook);
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
            //TODO Open the activity to create a new Textbook

            Toast.makeText(getActivity(), "Making a new Textbook", Toast.LENGTH_SHORT).show();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
