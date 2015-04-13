package edu.ua.collegeswap.view;

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
import edu.ua.collegeswap.database.SubleaseAccessor;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Sublease;

/**
 * Fragment to display a list of Sublease listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class FragmentSubleases extends SectionFragment implements View.OnClickListener {

    private List<Sublease> subleases;

    public FragmentSubleases() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment_subleases, container, false);

        updateSubleasesFromServer();

        // Get a reference to the linear layout which will hold the View for each sublease
        LinearLayout linearLayoutSubleases = (LinearLayout) view.findViewById(R.id.linearLayoutSubleases);

        for (Sublease s : subleases) {
            // Inflate the individual sublease View and put it inside the parent LinearLayout
            // (this returns the parent linearLayoutSubleases, not the individual child View we need)
            inflater.inflate(R.layout.individual_sublease, linearLayoutSubleases);

            // Get a reference to the individual bookmark view we just inflated
            LinearLayout subleaseView = (LinearLayout) linearLayoutSubleases.getChildAt(
                    linearLayoutSubleases.getChildCount() - 1);

            subleaseView.setTag(s); // Store the Sublease object as a tag in the View for retrieval when clicked
            subleaseView.setOnClickListener(this); // allow the user to click on the whole Sublease representation

            // Find the TextViews and their their text from the Sublease fields

            TextView title = (TextView) subleaseView.findViewById(R.id.AptName);
            title.setText(s.getTitle());

            TextView dateRange = (TextView) subleaseView.findViewById(R.id.textViewDateRange);
            dateRange.setText(s.getDateRange());

            TextView price = (TextView) subleaseView.findViewById(R.id.textPriceLabel);
            price.setText("$" + s.getAskingPrice());
        }

        return view;
    }

    private void updateSubleasesFromServer() {
        // Retrieve the list of subleases from the server
        SubleaseAccessor accessor = new SubleaseAccessor();
        List<Listing> listings = accessor.getAll();
        subleases = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Sublease) {
                subleases.add((Sublease) l);
            } else {
                throw new IllegalStateException("Expected a Sublease, but found another class.");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Sublease) {
            // Retrieve the Sublease stored in this View
            Sublease sublease = (Sublease) v.getTag();

            callbacks.onListingClicked(sublease);
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
            //TODO Open the activity to create a new Sublease

            Toast.makeText(getActivity(), "Making a new Sublease", Toast.LENGTH_SHORT).show();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
