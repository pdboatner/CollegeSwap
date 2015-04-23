package edu.ua.collegeswap.database;

import java.util.Calendar;

import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Created by Patrick on 4/9/2015.
 */
public class TicketWriter extends ListingWriter {
    @Override
    public void saveNew(Listing newListing) {
        if (newListing instanceof Ticket) {
            Ticket t = (Ticket) newListing;

            String args = "";
            args += "game|" + t.getGame();
            args += "|bowl|" + t.getBowl();
            Calendar c = Calendar.getInstance();
            args += "|date|" + c.getTimeInMillis();
            args += "|poster|" + t.getPosterAccount().getName();
            args += "|price|" + t.getAskingPriceFormatted();
            args += "|details|" + t.getDetails();
            args += "|title|" + t.getTitle();

            sendRequest(tableTicket, args);

        } else {
            throw new IllegalStateException("Expected a Ticket, but found another Object");
        }
    }

    @Override
    public void updateExisting(Listing updatedListing) {
        if (updatedListing instanceof Ticket) {
            Ticket t = (Ticket) updatedListing;

            String args = "";
            args += "game|" + t.getGame();
            args += "|bowl|" + t.getBowl();
            Calendar c = Calendar.getInstance();
            args += "|date|" + c.getTimeInMillis();
            args += "|price|" + t.getAskingPriceFormatted();
            args += "|details|" + t.getDetails();
            args += "|title|" + t.getTitle();

            sendRequest(tableTicket, t.getID(), args);
        } else {
            throw new IllegalStateException("Expected a Ticket, but found another Object");
        }
    }

    @Override
    public void deleteListing(Listing listingToDelete) {
        //TODO
    }
}
