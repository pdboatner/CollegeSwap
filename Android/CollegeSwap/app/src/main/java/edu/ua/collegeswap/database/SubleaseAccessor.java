package edu.ua.collegeswap.database;

import android.util.JsonReader;

import java.io.StringReader;
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
    public List<Listing> mockGetAll() {
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

    /**
     * Parse the JSON string and create a list of subleases
     *
     * @param json a JSON string returned from the server
     * @return a list of subleases populated with the information from the JSON string
     */
    private List<Listing> getSubleasesFromJSON(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));

        List<Listing> l = new ArrayList<>();

        // Parse the JSON
        try {
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("sublease")) {
                    // Read the array of subleases
                    reader.beginArray();

                    while (reader.hasNext()) {
                        // Read each sublease
                        Sublease s = new Sublease();
                        s.setTitle("Example Title");

                        reader.beginObject();
                        while (reader.hasNext()) {
                            // Read each field of the sublease

                            String fieldName = reader.nextName();
                            switch (fieldName) {
                                case "location":
                                    s.setLocation(reader.nextString());
                                    break;
                                case "startDate":
                                    Calendar c = Calendar.getInstance();
                                    c.setTimeInMillis(Long.parseLong(reader.nextString()));

                                    s.setStartDate(c);
                                    break;
                                case "endDate":
                                    Calendar c2 = Calendar.getInstance();
                                    c2.setTimeInMillis(Long.parseLong(reader.nextString()));

                                    s.setEndDate(c2);
                                    break;
                                case "poster":
                                    Account a = new Account();
                                    a.setName(reader.nextString());
                                    s.setPosterAccount(a);
                                    break;
                                case "price":
                                    s.setAskingPrice(Float.parseFloat(reader.nextString()));
                                    break;
                                case "title":
                                    s.setTitle(reader.nextString());
                                    break;
                                case "details":
                                    s.setDetails(reader.nextString());
                                    break;
                                default:
                                    reader.nextString();
                                    break;
                            }
                        }
                        reader.endObject();

                        l.add(s);
                    }

                    reader.endArray();
                }
            }

            reader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return l;

    }

    @Override
    public List<Listing> getAll() {
        String json = getJSONrequest(tableSublease, "");

        return getSubleasesFromJSON(json);
    }

    @Override
    public List<Listing> getByPrice(int minPrice, int maxPrice) {
        String json = getJSONrequest(tableSublease, "price>" + minPrice + ",price<" + maxPrice);

        return getSubleasesFromJSON(json);
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
        String json = getJSONrequest(tableSublease, "location=" + location);

        return castListingsToSubleases(getSubleasesFromJSON(json));
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
        String json = getJSONrequest(tableSublease, "price>" + minPrice + ",price<" + maxPrice + ",location=" + location);

        return castListingsToSubleases(getSubleasesFromJSON(json));
    }

    public List<Sublease> castListingsToSubleases(List<Listing> listings) {
        List<Sublease> subleases = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Sublease) {
                subleases.add((Sublease) l);
            } else {
                throw new IllegalStateException("Expected a Sublease, but found another class.");
            }
        }

        return subleases;
    }
}
