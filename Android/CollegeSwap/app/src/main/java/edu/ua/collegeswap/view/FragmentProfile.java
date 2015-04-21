package edu.ua.collegeswap.view;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        if (account == null){
            account = new Account();
            account.setName("Failure");
        }

        TextView usernameTextView = (TextView) view.findViewById(R.id.textViewAccountName);
        usernameTextView.setText(account.getName());

        return view;
    }


    @Override
    public void onClick(View v) {
        // TODO user clicks on an item in their list of postings

        if (v.getTag() instanceof Ticket) {
            // Retrieve the ticket stored in this view
            Ticket ticket = (Ticket) v.getTag();
            // Do something

        }else if (v.getTag() instanceof Textbook) {
            // Retrieve the textbook stored in this view
            Textbook textbook = (Textbook) v.getTag();
            // Do something
            
        }else if (v.getTag() instanceof Sublease) {
            // Retrieve the sublease stored in this view
            Sublease sublease = (Sublease) v.getTag();
            // Do something

        }else {
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

            case (R.id.action_edit) :
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