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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.SubleaseAccessor;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Sublease;

/**
 * Fragment to display a list of Sublease listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class FragmentSubleases extends SectionFragment implements View.OnClickListener, SectionFragment.ListingSectionFragment {

//    private List<Sublease> subleases;

    // UI references
    private EditText editTextMinPrice, editTextMaxPrice;
    private Spinner location;

    // State representation
    protected boolean filterByPrice = false;
    protected float minFilterPrice, maxFilterPrice;
    protected boolean filterByLocation = false;
    protected String filterLocation;
    protected boolean onlyShowForUser = false;

    public FragmentSubleases() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        checkLogin();

        final View view = inflater.inflate(R.layout.section_fragment_subleases, container, false);
        // Get a reference to the linear layout which will hold the View for each sublease
        LinearLayout linearLayoutSubleases = (LinearLayout) view.findViewById(R.id.linearLayoutSubleases);
        final View.OnClickListener onClickListener = this;

        if (!onlyShowForUser) {
            Button buttonClear = (Button) view.findViewById(R.id.buttonClear);
            Button buttonFilter = (Button) view.findViewById(R.id.buttonFilter);
            buttonClear.setOnClickListener(this);
            buttonFilter.setOnClickListener(this);

            editTextMinPrice = (EditText) view.findViewById(R.id.editTextMinPrice);
            editTextMaxPrice = (EditText) view.findViewById(R.id.editTextMaxPrice);
            location = (Spinner) view.findViewById(R.id.spinnerLocation);

            // Populate the spinner.  Duplicate code from EditSubleaseActivity.
            List<String> locations = new SubleaseAccessor().getLocations();
            locations.add(0, "Choose location");
            ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, locations);
            locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            location.setAdapter(locationAdapter);
        } else {
            // Hide the filtering controls
            view.findViewById(R.id.filterControlsSubleases).setVisibility(View.GONE);

            // Remove the padding
            View wholeView = view.findViewById(R.id.linearLayoutWholeSubleases);
            wholeView.setPadding(0, 0, 0, 0);
        }


        // Load the subleases
        reloadView(inflater, linearLayoutSubleases, onClickListener);

        return view;
    }

    /**
     * @param inflater              used to inflate each individual sublease layout
     * @param linearLayoutSubleases holds the subleases
     * @param onClickListener       for clicking on subleases
     */
    private void reloadView(final LayoutInflater inflater, final LinearLayout linearLayoutSubleases, final View.OnClickListener onClickListener) {

        new AsyncTask<Void, Void, List<Sublease>>() {

            @Override
            protected List<Sublease> doInBackground(Void... params) {
                return updateSubleasesFromServer();
            }

            @Override
            protected void onPostExecute(List<Sublease> subleases) {

                linearLayoutSubleases.removeAllViews(); // Remove the child views, since this can be called again after filtering

                for (Sublease s : subleases) {
                    // Inflate the individual sublease View and put it inside the parent LinearLayout
                    // (this returns the parent linearLayoutSubleases, not the individual child View we need)
                    inflater.inflate(R.layout.individual_sublease, linearLayoutSubleases);

                    // Get a reference to the individual bookmark view we just inflated
                    LinearLayout subleaseView = (LinearLayout) linearLayoutSubleases.getChildAt(
                            linearLayoutSubleases.getChildCount() - 1);

                    subleaseView.setTag(s); // Store the Sublease object as a tag in the View for retrieval when clicked
                    subleaseView.setOnClickListener(onClickListener); // allow the user to click on the whole Sublease representation

                    // Find the TextViews and their their text from the Sublease fields

                    TextView title = (TextView) subleaseView.findViewById(R.id.textViewTitle);
                    title.setText(s.getTitle());

                    TextView dateRange = (TextView) subleaseView.findViewById(R.id.textViewDateRange);
                    dateRange.setText(s.getDateRange());

                    TextView price = (TextView) subleaseView.findViewById(R.id.textViewPrice);
                    price.setText(s.getAskingPriceDollars());
                }
            }
        }.execute();
    }


    private List<Sublease> updateSubleasesFromServer() {
        // Retrieve the list of subleases from the server
        SubleaseAccessor accessor = new SubleaseAccessor();

        List<Listing> listings;

        if (onlyShowForUser) {
            listings = accessor.getByUser(account);
        } else {
            if (filterByPrice) {
                if (filterByLocation) {
                    return accessor.get(filterLocation, (int) minFilterPrice, (int) maxFilterPrice);
                } else {
                    listings = accessor.getByPrice((int) minFilterPrice, (int) maxFilterPrice);
                }
            } else {
                if (filterByLocation) {
                    return accessor.getByLocation(filterLocation);
                } else {
                    listings = accessor.getAll();
                }
            }
        }

        List<Sublease> subleases = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Sublease) {
                subleases.add((Sublease) l);
            } else {
                throw new IllegalStateException("Expected a Sublease, but found another class.");
            }
        }

        return subleases;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonClear:
                // Clear the UI filtering inputs
                editTextMinPrice.setText("");
                editTextMaxPrice.setText("");
                location.setSelection(0);

                // Set the state variables to show all subleases
                filterByPrice = false;
                filterByLocation = false;

                reloadView();
                break;
            case R.id.buttonFilter:
                // Set the state variables to possibly filter subleases
                try {
                    minFilterPrice = Float.parseFloat(editTextMinPrice.getText().toString());
                    maxFilterPrice = Float.parseFloat(editTextMaxPrice.getText().toString());

                    filterByPrice = true;
                } catch (Exception e) {
                    filterByPrice = false;
                }

                if (location.getSelectedItemPosition() != 0) {
                    filterLocation = (String) location.getSelectedItem();
                    filterByLocation = true;
                } else {
                    filterByLocation = false;
                }

                reloadView();
                break;
            default:
                if (v.getTag() instanceof Sublease) {
                    // Retrieve the Sublease stored in this View
                    Sublease sublease = (Sublease) v.getTag();

                    callbacks.onListingClicked(sublease);
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
            //Open the activity to create a new Sublease
            ((MainDrawerActivity) getActivity()).launchNewListingActivity(EditSubleaseActivity.class);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void reloadView() {
        reloadView(getActivity().getLayoutInflater(), (LinearLayout) getActivity().findViewById(R.id.linearLayoutSubleases), this);
    }

    @Override
    public void onlyShowForUser() {
        onlyShowForUser = true;
    }
}
