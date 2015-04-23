package edu.ua.collegeswap.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.AccountAccessor;
import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Sublease;
import edu.ua.collegeswap.viewModel.Textbook;
import edu.ua.collegeswap.viewModel.Ticket;


/**
 * A simple {@link Fragment} subclass.
 * The FragmentProfile class displays the current user's profile
 */
public class FragmentProfile extends SectionFragment implements View.OnClickListener {

    private AccountAccessor accountAccessor;
    private Account account;
    private Context context;

    private final String LOG_TAG = FragmentProfile.class.getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    public FragmentProfile() {
        // TODO Retrieve the user that is currently logged in

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Add this to tell the fragment that it has menu items to inflate
        setHasOptionsMenu(true);

        accountAccessor = new AccountAccessor();
        account = new Account();

        account = accountAccessor.getCachedLogin(context);
        if (account == null) {
            account = new Account();
            account.setName("Failure");
        }

        TextView usernameTextView = (TextView) view.findViewById(R.id.textViewAccountName);
        usernameTextView.setText(account.getName());

        // Set up the SectionFragments to show the user's listings
        FragmentManager fm = getChildFragmentManager();
        Fragment sectionTextbook = fm.findFragmentByTag("sectionTextbook");
        Fragment sectionTicket = fm.findFragmentByTag("sectionTicket");
        Fragment sectionSublease = fm.findFragmentByTag("sectionSublease");

        if (sectionTextbook == null ||
                sectionTicket == null ||
                sectionSublease == null) {
            // Create them for the first time
            sectionTextbook = SectionFragment.newInstance(SectionFragment.textbookNumber);
            sectionTicket = SectionFragment.newInstance(SectionFragment.ticketNumber);
            sectionSublease = SectionFragment.newInstance(SectionFragment.subleaseNumber);

            ((ListingSectionFragment) sectionTextbook).onlyShowForUser();
            ((ListingSectionFragment) sectionTicket).onlyShowForUser();
            ((ListingSectionFragment) sectionSublease).onlyShowForUser();

            fm.beginTransaction()
                    .replace(R.id.containerTextbooks, sectionTextbook, "sectionTextbook")
                    .replace(R.id.containerTickets, sectionTicket, "sectionTicket")
                    .replace(R.id.containerSubleases, sectionSublease, "sectionSublease")
                    .commit();
        }

        return view;
    }


    @Override
    public void onClick(View v) {
        // TODO user clicks on an item in their list of postings

        if (v.getTag() instanceof Textbook) {
            // Retrieve the textbook stored in this view
            Textbook textbook = (Textbook) v.getTag();
            // Do something

        } else if (v.getTag() instanceof Ticket) {
            // Retrieve the ticket stored in this view
            Ticket ticket = (Ticket) v.getTag();
            // Do something

        } else if (v.getTag() instanceof Sublease) {
            // Retrieve the sublease stored in this view
            Sublease sublease = (Sublease) v.getTag();
            // Do something

        } else {
            Log.e(LOG_TAG, "Unrecognizable object");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the options menu
        inflater.inflate(R.menu.menu_profile, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If user selects to edit their profile,,
        // Open the edit profile activity
        switch (item.getItemId()) {

            case (R.id.action_edit):
                //Toast.makeText(getActivity(), "Edit profile clicked", Toast.LENGTH_SHORT).show();
                editButtonClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * If the user selects the edit button in the options menu then
     * launch the intent to start EditProfileActivity
     */
    private void editButtonClick() {
        // Launch explicit intent to open the edit profile activity
        Intent editProfileIntent = new Intent(getActivity(), EditProfileActivity.class);
        editProfileIntent.putExtra(EditProfileActivity.EXTRA_ACCOUNT, account);
        startActivity(editProfileIntent);
    }
}