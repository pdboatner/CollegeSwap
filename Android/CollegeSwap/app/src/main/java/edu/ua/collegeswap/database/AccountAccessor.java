package edu.ua.collegeswap.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

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
            String[] splits = username.split("@");
            account.setName(splits[0]);
            account.setEmail(username);
            return account;
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
    public boolean checkLoginCredentials(String username, String password) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://www.bama.ua.edu/~cppopovich/CS495/login.php");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("name", username));
        pairs.add(new BasicNameValuePair("pass", password));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);
            String responseText = EntityUtils.toString(response.getEntity());
            return responseText.equalsIgnoreCase("success");
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
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
