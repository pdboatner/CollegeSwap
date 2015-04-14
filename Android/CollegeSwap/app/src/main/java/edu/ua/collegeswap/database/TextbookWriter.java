package edu.ua.collegeswap.database;

import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Created by Patrick on 4/9/2015.
 */
public class TextbookWriter extends ListingWriter {
    @Override
    public void saveNew(Listing newListing) {
        //TODO
    }

    @Override
    public void updateExisting(Listing updatedListing) {
        //TODO

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
