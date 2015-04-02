package edu.ua.collegeswap.database;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Created by Patrick on 3/25/2015.
 */
public class TextbookAccessor extends ListingAccessor {

    private List<Listing> mockGetTextbooks() {
        List<Listing> l = new ArrayList<>();

        Textbook t;
        for (int i = 0; i < 5; i++) {
            t = new Textbook();
            t.setCourseSubject("MATH");
            t.setCourseNumber(145);
            t.setAskingPrice(110);
            t.setTitle("Calculus book for sale");
            t.setDetails("Pretty good condition.");
            l.add(t);

            t = new Textbook();
            t.setCourseSubject("CS");
            t.setCourseNumber(360);
            t.setAskingPrice(30);
            t.setTitle("Algorithms book. Very used.");
            t.setDetails("Took the class three times. Got a lot of wear and tear.");
            l.add(t);

            t = new Textbook();
            t.setCourseSubject("ENGR");
            t.setCourseNumber(101);
            t.setAskingPrice(10);
            t.setTitle("Please take this intro book");
            t.setDetails("Why, oh why did I ever buy this?");
            l.add(t);
        }

        return l;
    }

    /**
     * @param url
     * @return a list of textbooks from the given URL
     */
    private List<Listing> getTextbooksFromURL(String url) {
        String json = getJSONfromURL(url);
        JsonReader reader = new JsonReader(new StringReader(json));

        List<Listing> l = new ArrayList<>();

        // Parse the JSON
        try {
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("textbook")) {
                    // Read the array of textbooks
                    reader.beginArray();

                    while (reader.hasNext()) {
                        // Read each textbook
                        Textbook t = new Textbook();

                        reader.beginObject();
                        while (reader.hasNext()) {
                            // Read each field of the textbook

                            String fieldName = reader.nextName();
                            if (fieldName.equals("subject")) {
                                t.setCourseSubject(reader.nextString());
                            } else if (fieldName.equals("number")) {
                                t.setCourseNumber(Integer.parseInt(reader.nextString()));
                            } else {
                                reader.nextString();
                            }
                            //TODO Price
                            //TODO Title
                        }
                        reader.endObject();

                        t.setAskingPrice(50);
                        t.setTitle("Example Title");

                        l.add(t);
                    }

                    reader.endArray();
                }
            }

            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
//        return mockGetTextbooks();
    }

    @Override
    public List<Listing> getAll() {
        return getTextbooksFromURL("http://bama.ua.edu/~cppopovich/CS495/request.php");
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
