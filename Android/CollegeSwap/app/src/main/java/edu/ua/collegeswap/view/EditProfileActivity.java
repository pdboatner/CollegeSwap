package edu.ua.collegeswap.view;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import edu.ua.collegeswap.R;

/* The EditProfileActivity class allows the user to edit their profile.
 * This activity is launched via the FragmentProfile.
 */

public class EditProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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
        }
        else if (id == R.id.action_save) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String username = prefs.getString(getString(R.string.pref_username_key), getString(R.string.pref_username_default));

        }

        return super.onOptionsItemSelected(item);
    }
}
