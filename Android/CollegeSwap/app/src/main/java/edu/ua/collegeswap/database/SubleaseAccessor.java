package edu.ua.collegeswap.database;

import java.util.ArrayList;
import java.util.Calendar;
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

        List<Listing> l = new ArrayList<>();

        Sublease s;
        Calendar start, end;
        for (int i = 0; i < 5; i++) {
            s = new Sublease();
            s.setAskingPrice(450);
            s.setTitle("Woodlands, 1 bedroom");
            s.setDetails("You get a pool and stuff.");
            s.setLocation("The Woodlands");
            start = Calendar.getInstance();
            end = Calendar.getInstance();
            start.add(Calendar.DATE, 2);
            end.add(Calendar.DATE, 90);
            s.setStartDate(start);
            s.setEndDate(end);
            l.add(s);

            s = new Sublease();
            s.setAskingPrice(300);
            s.setTitle("Highlands, 2 bedrooms");
            s.setDetails("You're almost still on campus!");
            s.setLocation("The Highlands");
            start = Calendar.getInstance();
            end = Calendar.getInstance();
            start.add(Calendar.DATE, 7);
            end.add(Calendar.DATE, 100);
            s.setStartDate(start);
            s.setEndDate(end);
            l.add(s);

            s = new Sublease();
            s.setAskingPrice(200);
            s.setTitle("Grandparent's spare bedroom");
            s.setDetails("They love young people, and make great cookies.");
            s.setLocation("Other");
            start = Calendar.getInstance();
            end = Calendar.getInstance();
            end.add(Calendar.DATE, 120);
            s.setStartDate(start);
            s.setEndDate(end);
            l.add(s);
        }

        return l;

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
        l.add("The Highlands");
        l.add("Other");

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
