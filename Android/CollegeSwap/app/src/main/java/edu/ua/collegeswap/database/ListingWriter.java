package edu.ua.collegeswap.database;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.viewModel.Listing;

/**
 * Accepts Listing objects from the View and sends the data to the server.
 * Created by Patrick on 4/9/2015.
 */
public abstract class ListingWriter {

    protected static final String tableAccount = ListingAccessor.tableAccount;
    protected static final String tableTextbook = ListingAccessor.tableTextbook;
    protected static final String tableTicket = ListingAccessor.tableTicket;
    protected static final String tableSublease = ListingAccessor.tableSublease;

    public static void sendRequest(String table, String args) {
        sendRequest(table, -1, args);
    }

    /**
     * Request the JSON from the server at "bama.ua.edu/~cppopovich/CS495/request.php".
     *
     * @param table one of (account,textbook,ticket,sublease)
     * @param key   when editing, the id # of the listing.
     *              When creating a new listing, 0 or less.
     * @param args  is a pipe-separated list of data (pipe='|').
     *              When editing a listing, only the data to be modified needs to be included.
     */
    public static void sendRequest(String table, int key, String args) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();


        HttpPost httppost = new HttpPost(
                (key > 0) ? getEditScriptURL() : getAddScriptURL());

        try {
            // Add the data
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("tbl", table));

            // Optionally add the key options
            if (key > 0) {
                nameValuePairs.add(new BasicNameValuePair("key", "" + key));
            }

            nameValuePairs.add(new BasicNameValuePair("args", args));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the new listing by sending all the fields to the server.
     *
     * @param newListing a newly created listing
     */
    public abstract void saveNew(Listing newListing);

    /**
     * Send the fields from this existing listing to the server.
     *
     * @param updatedListing a Listing object whose fields have been updated in some way. The ID
     *                       will still be the same as the copy on the server.
     */
    public abstract void updateExisting(Listing updatedListing);

    /**
     * Delete this listing from the server.
     *
     * @param listingToDelete The ID will still be the same as the copy on the server.
     */
    public abstract void deleteListing(Listing listingToDelete);

    public static String getAddScriptURL() {
        return "http://bama.ua.edu/~cppopovich/CS495/add.php";
    }

    public static String getEditScriptURL() {
        return "http://bama.ua.edu/~cppopovich/CS495/edit.php";
    }

}
