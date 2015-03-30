package edu.ua.collegeswap.view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.AccountAccessor;
import edu.ua.collegeswap.viewModel.Account;



/**
 * A simple {@link Fragment} subclass.
 * The FragmentProfile class displays the current user's profile
 */
public class FragmentProfile extends SectionFragment implements View.OnClickListener {

    private Account account;

    public FragmentProfile() {
        // TODO Retrieve the user that is currently logged in
        AccountAccessor accessor = new AccountAccessor();
        //Account account = accessor.getLogin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Add this to tell the fragment that it has menu items to inflate
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onClick(View v) {

        // TODO user clicks on an item in the list

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
                // Make toast for now...
                //Toast.makeText(getActivity(), "Edit profile clicked", Toast.LENGTH_SHORT).show();
                editButtonClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editButtonClick() {
        // Launch explicit intent to open the edit profile activity
        Intent editProfileIntent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(editProfileIntent);
    }
}