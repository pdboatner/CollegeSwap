package edu.ua.collegeswap.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import edu.ua.collegeswap.viewModel.Account;

/**
 * Created by justinkerber on 3/26/15.
 */
public class AccountAccessor {

    public static final String MY_PREFS = "MyPrefs";
    public static final String usernameKey = "usernameKey";
    public static final String passwordKey = "passwordKey";

    public Account account;

    private static final String LOG_TAG = AccountAccessor.class.getSimpleName();

    public Account getLogin(String s, String p) {
        //TODO get the login information for the user
        return null;
    }

    /**
     * Use a SharedPreferences to try to get the stored login info.
     *
     * @return the Account (if the user has it stored in shared preferences) or null otherwise
     */
    public Account getCachedLogin(Context context) {
        account = new Account();

        String username = loadPreferences(context, usernameKey);
        String password = loadPreferences(context, passwordKey);

        if (username != null && password != null) {
            if (checkLoginCredentials(username, password)) {
                String[] splits = username.split("@");
                account.setName(splits[0]);
                account.setEmail(username);
                return account;
            }
        }
        return null;
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

        return true;
    }

    /**
     * Method used to get Shared Preferences
     */

    public SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    }

    /**
     * Method used to load Preferences
     */
    public String loadPreferences(Context context, String key) {
        try {
            SharedPreferences sharedPreferences = getPreferences(context);
            if (sharedPreferences.contains(key)) {
                return sharedPreferences.getString(key, "");
            } else return null;
        } catch (NullPointerException nullPointerException) {
            Log.e(LOG_TAG, "nullPointerException");
            return null;
        }
    }

    /**
     * Method used to delete Preferences
     */
    public boolean deletePreferences(Context context, String key) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        //editor.remove(key).commit();
        editor.remove(key).apply();
        return false;
    }

}
