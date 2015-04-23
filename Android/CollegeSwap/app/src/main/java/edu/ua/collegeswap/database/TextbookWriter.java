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
            args += "|title|" + t.getTitle();

            sendRequest(tableTextbook, args);
        } else {
            throw new IllegalStateException("Expected a Textbook, but found another Object");
        }
    }

    @Override
    public void updateExisting(Listing updatedListing) {
        if (updatedListing instanceof Textbook) {
            Textbook t = (Textbook) updatedListing;

            String args = "";
            args += "subject|" + t.getCourseSubject();
            args += "|number|" + t.getCourseNumber();
            Calendar c = Calendar.getInstance();
            args += "|date|" + c.getTimeInMillis();
            args += "|price|" + t.getAskingPriceFormatted();
            args += "|details|" + t.getDetails();
            args += "|title|" + t.getTitle();

            sendRequest(tableTextbook, t.getID(), args);
        } else {
            throw new IllegalStateException("Expected a Textbook, but found another Object");
        }
    }

    @Override
    public void deleteListing(Listing listingToDelete) {
        //TODO
    }
}
