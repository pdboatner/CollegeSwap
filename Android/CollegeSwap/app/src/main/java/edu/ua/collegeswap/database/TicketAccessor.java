package edu.ua.collegeswap.database;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Created by Patrick on 3/25/2015.
 */
public class TicketAccessor extends ListingAccessor {
    @Override
    public List<Listing> getAll() {
        //TODO

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
     * @param game the name of the football game.  See TicketAccessor.getGames()
     * @param bowl "upper" or "lower"
     * @return a list of all Ticket Listings for a given game
     */
    public List<Ticket> getByGame(String game, String bowl) {
        //TODO
        return null;
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
        //TODO
        return null;
    }
}
