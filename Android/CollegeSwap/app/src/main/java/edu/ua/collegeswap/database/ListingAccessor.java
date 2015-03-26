package edu.ua.collegeswap.database;

import java.util.List;

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
}
