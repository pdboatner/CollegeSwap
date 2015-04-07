package edu.ua.collegeswap.view;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.ua.collegeswap.R;

/* The EditProfileActivity class allows the user to edit their profile.
 * This activity is launched via the FragmentProfile.
 */

public class EditProfileActivity extends ActionBarActivity {

    private static final String PREFS_NAME = "MyPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        EditText phoneNum = (EditText) findViewById(R.id.editTextPhoneNumber);
        EditText email = (EditText) findViewById(R.id.editTextEmailAddress);

        phoneNum.setText("256-123-4567");
        email.setText("soccerMom203@crimson.ua.edu");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        } else if (id == R.id.action_save) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String username = prefs.getString(getString(R.string.pref_username_key), getString(R.string.pref_username_default));
            saveButtonClicked();

        }
        // Respond to the action bar's Up/Home button
        else if (id == android.R.id.home) {
            /*
            This prevents the MainDrawerActivity from reverting to the default Section number.
            This makes the Up/Home button behavior identical to the hardware Back button behavior.
            */
            finish();
            return true;
        }

//        switch(item.getItemId()) {
//            case (R.id.action_save) :
//                saveButtonClicked();
//                break;
//            case (R.id.action_settings) :
//                break;
//        }

        return super.onOptionsItemSelected(item);
    }


    private void saveButtonClicked() {
          Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show();
          SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putString(getString(R.string.pref_username_key), "newUser");
    }
}
