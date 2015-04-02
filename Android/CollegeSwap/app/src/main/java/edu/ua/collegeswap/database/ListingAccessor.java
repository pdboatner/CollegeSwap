package edu.ua.collegeswap.database;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
}
