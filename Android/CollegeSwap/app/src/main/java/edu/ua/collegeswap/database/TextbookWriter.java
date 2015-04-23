package edu.ua.collegeswap.database;

import java.util.Calendar;

import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Created by Patrick on 4/9/2015.
 */
public class TextbookWriter extends ListingWriter {
    @Override
    public void saveNew(Listing newListing) {
        if (newListing instanceof Textbook) {
            Textbook t = (Textbook) newListing;

            String args = "";
            args += "subject|" + t.getCourseSubject();
            args += "|number|" + t.getCourseNumber();
            Calendar c = Calendar.getInstance();
            args += "|date|" + c.getTimeInMillis();
            args += "|poster|" + t.getPosterAccount().getName();
            args += "|price|" + t.getAskingPriceFormatted();
            args += "|details|" + t.getDetails();

            sendRequest(tableTextbook, args);
        }
    }

    @Override
    public void updateExisting(Listing updatedListing) {
        Textbook textbook = (Textbook) updatedListing;

        // TODO Get the information from the textbook and send it to the server
        textbook.getTitle();
        textbook.getAskingPrice();
        textbook.getCourseNumber();
        textbook.getCourseSubject();


    }

    @Override
    public void deleteListing(Listing listingToDelete) {
        //TODO
    }
}
