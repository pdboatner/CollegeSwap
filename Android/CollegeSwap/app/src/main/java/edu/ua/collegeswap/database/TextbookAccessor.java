package edu.ua.collegeswap.database;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Created by Patrick on 3/25/2015.
 */
public class TextbookAccessor extends ListingAccessor {
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
     * @param courseSubject like "MATH"
     * @param courseNumber  like 125
     * @return a list of all Textbook Listings for a given class
     */
    public List<Textbook> getByClass(String courseSubject, int courseNumber) {
        //TODO
        return null;
    }

    /**
     * @return a list of course subjects
     */
    public List<String> getCourseSubjects() {
        //TODO
        List<String> l = new ArrayList<>();
        l.add("MATH");
        l.add("PY");
        l.add("CS");
        l.add("ECE");

        return l;
    }

    /**
     * @param courseSubject like "MATH"
     * @return a list of course numbers for the given course subject, like 125
     */
    public List<Integer> getCourseNumbers(String courseSubject) {
        //TODO

        List<Integer> l = new ArrayList<>();
        l.add(101);
        l.add(105);
        l.add(400);

        return l;
    }

    /**
     * @param minPrice      the minimum price of listings to display
     * @param maxPrice      the maximum price of listings to display
     * @param courseSubject like "MATH"
     * @param courseNumber  like 125
     * @return a list of all Textbook Listings for a given class which also fall into a given price
     * range
     */
    public List<Textbook> get(int minPrice, int maxPrice, String courseSubject, int courseNumber) {
        //TODO
        return null;
    }
}
