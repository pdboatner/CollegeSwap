package edu.ua.collegeswap.database;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Listing;

/**
 * Retrieves Listings from the server and creates corresponding ViewModel objects.
 * <p/>
 * Created by Patrick on 3/25/2015.
 */
public abstract class ListingAccessor {

    protected static final String tableAccount = "account";
    protected static final String tableTextbook = "textbook";
    protected static final String tableTicket = "ticket";
    protected static final String tableSublease = "sublease";

    /**
     * @return a list of all Listings (of a specific subclass)
     */
    public abstract List<Listing> getAll();

    /**
     * @param minPrice the minimum price of listings to include
     * @param maxPrice the maximum price of listings to include
     * @return a list of all Listings (of a specific subclass) which fall within the
     * given price range
     */
    public abstract List<Listing> getByPrice(int minPrice, int maxPrice);

    /**
     * @param account the user's account. Only listings for this account will be retrieved
     * @return a list of all Listings (of a specific subclass) for a given user
     */
    public abstract List<Listing> getByUser(Account account);

    /**
     * Get the JSON string from the URL.
     *
     * @param urlString the complete URL
     * @return the JSON string, or "" if error occurred
     */
    public static String getJSONfromURL(String urlString) {
        int retry = 3;
        for (int i = 1; i <= retry; i++) {
            try {
                URL url = new URL(urlString);
                InputStream is = url.openStream();
                String output = "";
                Scanner s = new Scanner(is);
                while (s.hasNextLine()) {
                    output += s.nextLine();
                }
                s.close();
                return output;
            } catch (IOException e) {
                if (i == retry) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * Request the JSON from the server at "bama.ua.edu/~cppopovich/CS495/request.php".
     *
     * @param table one of (account,textbook,ticket,sublease)
     * @param args  a comma-separated list of conditions. For my convenience, the first value in
     *              each pair is assumed to be the field. The second is assumed to be the value.
     *              <p/>
     *              example: 'subject=CS,price>9,price<20'
     * @return the JSON response from the server, or "" if error occurred
     */
    public static String getJSONrequest(String table, String args) {
        return getJSONrequest(table, args, null);
    }

    /**
     * Request the JSON from the server at "bama.ua.edu/~cppopovich/CS495/request.php".
     *
     * @param table one of (account,textbook,ticket,sublease)
     * @param args  a comma-separated list of conditions. For my convenience, the first value in
     *              each pair is assumed to be the field. The second is assumed to be the value.
     *              <p/>
     *              example: 'subject=CS,price>9,price<20'
     * @param sort  the condition to sort by, prefaced by + or - . Can be null.
     *              <p/>
     *              example: '+price'
     * @return the JSON response from the server, or "" if error occurred
     */
    public static String getJSONrequest(String table, String args, String sort) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(getScriptURL());

        try {
            // Add the data
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("tbl", table));
            nameValuePairs.add(new BasicNameValuePair("args", args));

            // Optionally add the sorting options
            if (sort != null && sort.length() != 0) {
                nameValuePairs.add(new BasicNameValuePair("sort", sort));
            }

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            // Get the JSON string from the response
            InputStream inputStream = response.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            String bufferedStrChunk = null;

            while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferedStrChunk);
            }

            return stringBuilder.toString();


        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getScriptURL() {
        return "http://bama.ua.edu/~cppopovich/CS495/request.php";
    }
}
