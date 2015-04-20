package edu.ua.collegeswap.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.ua.collegeswap.R;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO If there's a login in the SharedPreference, go straight to the main activity


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

        if (checkLoginCredentials(usernameLogin, passwordLogin)) {
            launchMainDrawerActivity();
        } else {
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
    private boolean checkLoginCredentials(String username, String password) {

        //TODO check the users login credentials against the server

        return true; // too much work to type things  <-- lazy! XD

//        if (username.equals("bob") && password.equals("1234")) {
//            return true;
//        } else {
//            return false;
//        }

    }

    /**
     * Launch the main drawer activity (has a navigation drawer to show different types of
     * listings).
     */
    private void launchMainDrawerActivity() {
        Intent i = new Intent(this, MainDrawerActivity.class);
        startActivity(i);
    }

}
