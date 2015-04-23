package edu.ua.collegeswap.database;

import java.util.Calendar;

import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Sublease;

/**
 * Created by Patrick on 4/9/2015.
 */
public class SubleaseWriter extends ListingWriter {
    @Override
    public void saveNew(Listing newListing) {
        if (newListing instanceof Sublease) {
            Sublease s = (Sublease) newListing;

            String args = "";
            args += "location|" + s.getLocation();
            Calendar c = Calendar.getInstance();
            args += "|date|" + c.getTimeInMillis();
            args += "|startDate|" + s.getStartDate().getTimeInMillis();
            args += "|endDate|" + s.getEndDate().getTimeInMillis();
            args += "|poster|" + s.getPosterAccount().getName();
            args += "|price|" + s.getAskingPriceFormatted();
            args += "|details|" + s.getDetails();
            args += "|title|" + s.getTitle();

            sendRequest(tableSublease, args);
        } else {
            throw new IllegalStateException("Expected a Sublease, but found another Object");
        }
    }

    @Override
    public void updateExisting(Listing updatedListing) {
        if (updatedListing instanceof Sublease) {
            Sublease s = (Sublease) updatedListing;

            String args = "";
            args += "location|" + s.getLocation();
            Calendar c = Calendar.getInstance();
            args += "|date|" + c.getTimeInMillis();
            args += "|startDate|" + s.getStartDate().getTimeInMillis();
            args += "|endDate|" + s.getEndDate().getTimeInMillis();
            args += "|price|" + s.getAskingPriceFormatted();
            args += "|details|" + s.getDetails();
            args += "|title|" + s.getTitle();

            sendRequest(tableSublease, s.getID(), args);
        } else {
            throw new IllegalStateException("Expected a Sublease, but found another Object");
        }
    }

    @Override
    public void deleteListing(Listing listingToDelete) {
        //TODO
    }
}
