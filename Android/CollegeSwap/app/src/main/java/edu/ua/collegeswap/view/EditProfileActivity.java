package edu.ua.collegeswap.view;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.viewModel.Account;

/* The EditProfileActivity class allows the user to edit their profile.
 * This activity is launched via the FragmentProfile.
 */

public class EditProfileActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String PREFS_NAME = "MyPreferences";

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setupActionBar();


        EditText phoneNum = (EditText) findViewById(R.id.editTextPhone);
        EditText email = (EditText) findViewById(R.id.editTextEmail);


        phoneNum.setHint("256-123-4567");
        email.setHint("soccerMom@crimson.ua.edu");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

//      else if (id == R.id.action_save) {
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//            String username = prefs.getString(getString(R.string.pref_username_key), getString(R.string.pref_username_default));
//            saveButtonClicked();
//
//        }


        return true;
    }


    private void saveButtonClicked() {
          Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show();
//          SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
//          SharedPreferences.Editor editor = sharedPreferences.edit();
//          editor.putString(getString(R.string.pref_username_key), "newUser");
    }

    /**
     * See https://plus.google.com/+RomanNurik/posts/R49wVvcDoEW and https://android.googlesource.com/platform/developers/samples/android/+/master/ui/actionbar/DoneBar
     */
    protected void setupActionBar() {
        final LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(R.layout.actionbar_done_cancel, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(this);
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(this);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_cancel:
                finish();
                break;
            case R.id.actionbar_done:
                // Save the Ticket. Submit it to the server.

//                ticket.setTitle(title.getText().toString());
//                ticket.setAskingPrice(Float.parseFloat(price.getText().toString()));
//                ticket.setDetails(details.getText().toString());
//                ticket.setBowl(bowl.getSelectedItem().toString());
//                ticket.setGame(game.getSelectedItem().toString());
//                //TODO check the indices of the spinners. Should not be 0 - the hint.
//
//                TicketWriter ticketWriter = new TicketWriter();
//                if (editingExisting) {
//                    ticketWriter.updateExisting(ticket);
//                    Toast.makeText(this, "Updating existing ticket", Toast.LENGTH_SHORT).show();
//                } else {
//                    ticketWriter.saveNew(ticket);
//                    Toast.makeText(this, "Saving new ticket", Toast.LENGTH_SHORT).show();
//                }

                saveButtonClicked();
                finish(); // TODO ask the calling activity to refresh now?

                break;
        }

    }
}
