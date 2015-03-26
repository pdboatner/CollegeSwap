package edu.ua.collegeswap.database;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Sublease;

/**
 * Created by Patrick on 3/25/2015.
 */
public class SubleaseAccessor extends ListingAccessor {
    @Override
    public List<Listing> getAll() {
        //TODO
        return null;
    }

    @Override
    public List<Listing> getByPrice(int minPrice, int maxPrice) {
        //TODO
        return null;
    }

    @Override
    public List<Listing> getByUser(Account account) {
        //TODO
        return null;
    }

    /**
     * @param location the location, like "The Retreat". See SubleaseAccessor.getLocations()
     * @return a list of Sublease Listings for a given location
     */
    public List<Sublease> getByLocation(String location) {
        //TODO
        return null;
    }

    /**
     * @return a list of Locations
     */
    public List<String> getLocations() {
        //TODO

        List<String> l = new ArrayList<>();
        l.add("The Retreat");
        l.add("The Woodlands");

        return l;
    }

    /**
     * @param location the location, like "The Retreat". See SubleaseAccessor.getLocations()
     * @param minPrice the minimum price of Listings to retrieve
     * @param maxPrice the maximum price of Listings to retrieve
     * @return a list of Sublease Listings for a given location which also fall within the given
     * price range
     */
    public List<Sublease> get(String location, int minPrice, int maxPrice) {
        //TODO
        return null;
    }
}
