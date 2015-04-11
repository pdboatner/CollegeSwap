package edu.ua.collegeswap.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.ua.collegeswap.R;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Called when the login button is pressed
     *
     * @param buttonLogin the login button
     */
    public void loginClicked(View buttonLogin) {
        EditText username = (EditText) findViewById(R.id.editText_usernameLogin);
        EditText password = (EditText) findViewById(R.id.editText_passwordLogin);
        String usernameLogin = username.getText().toString();
        String passwordLogin = password.getText().toString();

        if (checkLoginCredentials(usernameLogin, passwordLogin)) { launchMainDrawerActivity(); }
        else {
            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * After login button clicked, check the login credentials entered by the user.
     * If accepted, call launchMainDrawerActivity
     *
     * @param username the username for the user
     * @param password the password for the user
     */
    private boolean checkLoginCredentials(String username, String password){

        //TODO check the users login credentials against the server

        if ( username.equals("bob") && password.equals("1234") ) { return true; }
        else { return false; }

    }

    /**
     * Launch the main drawer activity (has a navigation drawer to show different types of
     * listings).
     */
    private void launchMainDrawerActivity() {
        Intent i = new Intent(this, MainDrawerActivity.class);
        startActivity(i);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
