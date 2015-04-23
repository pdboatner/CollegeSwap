package edu.ua.collegeswap.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.AccountAccessor;
import edu.ua.collegeswap.viewModel.Account;


public class LoginActivity extends Activity {

    public static final String MY_PREFS = "MyPrefs";
    public static final String usernameKey = "usernameKey";
    public static final String passwordKey = "passwordKey";
    private EditText username_editText, password_editText;

    public Account account;
    public AccountAccessor accountAccessor;

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context mContext = this;

        accountAccessor = new AccountAccessor();
        account = accountAccessor.getCachedLogin(mContext);
        if (account != null){
            Toast.makeText(this, account.getName(), Toast.LENGTH_SHORT).show(); // debugging purposes, DELETE
            launchMainDrawerActivity();
        }

    }

    /**
     * Called when the login button is pressed
     *
     * @param buttonLogin the login button
     */
    public void loginClicked(View buttonLogin) {
        username_editText = (EditText) findViewById(R.id.editText_usernameLogin);
        password_editText = (EditText) findViewById(R.id.editText_passwordLogin);
        String usernameLogin = username_editText.getText().toString();
        String passwordLogin = password_editText.getText().toString();
        checkLoginCredentials(usernameLogin, passwordLogin);
    }

    /**
     * After login button clicked, check the login credentials entered by the user.
     * If accepted, call launchMainDrawerActivity
     *
     * @param username the username for the user
     * @param password the password for the user
     */
    private boolean checkLoginCredentials(String username, String password) {

        final String name = username;
        final String pass = password;
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... params) {
                return accountAccessor.checkLoginCredentials(name, pass);
            }
            @Override
            protected void onPostExecute(Boolean result) {
                if(result){
                    savePreferences(usernameKey, name);
                    savePreferences(passwordKey, pass);
                    launchMainDrawerActivity();
                }else{
                    loginFailed();
                }
            }
        }.execute();
        return true; // too much work to type things  <-- lazy! XD
    }

    /**
     * Launch the main drawer activity (has a navigation drawer to show different types of
     * listings).
     */
    private void launchMainDrawerActivity() {
        Intent i = new Intent(this, MainDrawerActivity.class);
        startActivity(i);
    }

    /**
     * Display failed login alert.
     */
    private void loginFailed() {
        Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
    }

    /**
     *   Method used to get Shared Preferences */

    public SharedPreferences getPreferences()
    {
        return getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }
    /**
     *  Method used to save Preferences */
    public void savePreferences(String key, String value)
    {
        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
