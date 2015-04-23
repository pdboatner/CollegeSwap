package edu.ua.collegeswap.database;

import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Sublease;
import edu.ua.collegeswap.viewModel.Textbook;
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Created by Patrick on 4/22/2015.
 */
public class TransactionManager {

    /**
     * Send this offer to the server
     *
     * @param buyerUsername
     * @param listing
     * @param offerText     whatever the user typed into the input box. Just send this to the other
     *                      user via email.
     */
    public void makeOffer(String buyerUsername, Listing listing, String offerText) {

        int id = listing.getID();
        String sellerUsername = listing.getPosterAccount().getName();

        if (listing instanceof Ticket) {
            //TODO
        } else if (listing instanceof Textbook) {
            //TODO
        } else if (listing instanceof Sublease) {
            //TODO
        }
    }
}
