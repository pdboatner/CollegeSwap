package edu.ua.collegeswap.database;

import android.util.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Created by Patrick on 3/25/2015.
 */
public class TicketAccessor extends ListingAccessor {
    public List<Listing> mockGetAll() {
        List<Listing> l = new ArrayList<>();

        Ticket t;
        for (int i = 0; i < 5; i++) {
            t = new Ticket();
            t.setGame("Auburn");
            t.setBowl("Lower bowl");
            t.setAskingPrice(200);
            t.setTitle("Iron bowl for sale, transfer only");
            t.setDetails("it's a great ticket.");
            l.add(t);

            t = new Ticket();
            t.setGame("West Carolina");
            t.setBowl("Upper bowl");
            t.setAskingPrice(25);
            t.setTitle("WC for sale. Can upgrade.");
            t.setDetails("it's a pretty shabby ticket.");
            l.add(t);

            t = new Ticket();
            t.setGame("LSU");
            t.setBowl("Lower bowl");
            t.setAskingPrice(150);
            t.setTitle("LSU for sale. Upgraded.");
            t.setDetails("it's a nice ticket.");
            l.add(t);
        }

        return l;
    }

    /**
     * Parse the JSON string and create a list of tickets
     *
     * @param json a JSON string returned from the server
     * @return a list of tickets populated with the information from the JSON string
     */
    private List<Listing> getTicketsFromJSON(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));

        List<Listing> l = new ArrayList<>();

        // Parse the JSON
        try {
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("ticket")) {
                    // Read the array of tickets
                    reader.beginArray();

                    while (reader.hasNext()) {
                        // Read each ticket
                        Ticket t = new Ticket();
                        t.setTitle("Example Title");

                        reader.beginObject();
                        while (reader.hasNext()) {
                            // Read each field of the ticket

                            String fieldName = reader.nextName();
                            switch (fieldName) {
                                case "game":
                                    t.setGame(reader.nextString());
                                    break;
                                case "bowl":
                                    t.setBowl(reader.nextString());
                                    break;
                                case "poster":
                                    Account a = new Account();
                                    a.setName(reader.nextString());
                                    t.setPosterAccount(a);
                                    break;
                                case "price":
                                    t.setAskingPrice(Float.parseFloat(reader.nextString()));
                                    break;
                                case "title":
                                    t.setTitle(reader.nextString());
                                    break;
                                case "details":
                                    t.setDetails(reader.nextString());
                                    break;
                                default:
                                    reader.nextString();
                                    break;
                            }
                        }
                        reader.endObject();

                        l.add(t);
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
        String json = getJSONrequest(tableTicket, "");

        return getTicketsFromJSON(json);
    }

    @Override
    public List<Listing> getByPrice(int minPrice, int maxPrice) {
        String json = getJSONrequest(tableTicket, "price>" + minPrice + ",price<" + maxPrice);

        return getTicketsFromJSON(json);
    }

    @Override
    public List<Listing> getByUser(Account account) {
        //TODO
        return null;
    }

    /**
     * @param game the name of the football game.  See TicketAccessor.getGames()
     * @param bowl see TicketAccesor.getBowls().
     * @return a list of all Ticket Listings for a given game
     */
    public List<Ticket> getByGame(String game, String bowl) {
        String json = getJSONrequest(tableTicket, "game=" + game + ",bowl=" + bowl);

        return castListingsToTickets(getTicketsFromJSON(json));
    }

    /**
     * @return a list of all games
     */
    public List<String> getGames() {
        //TODO

        List<String> l = new ArrayList<>();

        l.add("West Carolina");
        l.add("Auburn");
        l.add("LSU");

        return l;
    }

    /**
     * @return a list of the bowl options
     */
    public List<String> getBowls() {
        List<String> l = new ArrayList<>(2);

        l.add("Upper bowl");
        l.add("Lower bowl");

        return l;
    }

    /**
     * @param game     the name of the football game.  See TicketAccessor.getGames()
     * @param bowl     ""upper" or "lower". See TicketAccessor.getBowls()
     * @param minPrice the minimum price of Ticket Listings to return
     * @param maxPrice the maximum price of Ticket Listings to return
     * @return a list of all Ticket listings for a given game within the given price range
     */
    public List<Ticket> get(String game, String bowl, int minPrice, int maxPrice) {
        String json = getJSONrequest(tableTicket,
                "price>" + minPrice + ",price<" + maxPrice + ",game=" + game + ",bowl=" + bowl);

        return castListingsToTickets(getTicketsFromJSON(json));
    }

    public List<Ticket> castListingsToTickets(List<Listing> listings) {
        List<Ticket> tickets = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Ticket) {
                tickets.add((Ticket) l);
            } else {
                throw new IllegalStateException("Expected a Ticket, but found another class.");
            }
        }

        return tickets;
    }
}
