package edu.ua.collegeswap.database;

import edu.ua.collegeswap.viewModel.Listing;

/**
 * Accepts Listing objects from the View and sends the data to the server.
 * Created by Patrick on 4/9/2015.
 */
public abstract class ListingWriter {

    /**
     * Save the new listing by sending all the fields to the server.
     *
     * @param newListing a newly created listing
     */
    public abstract void saveNew(Listing newListing);

    /**
     * Send the fields from this existing listing to the server.
     *
     * @param updatedListing a Listing object whose fields have been updated in some way. The ID
     *                       will still be the same as the copy on the server.
     */
    public abstract void updateExisting(Listing updatedListing);

    /**
     * Delete this listing from the server.
     *
     * @param listingToDelete The ID will still be the same as the copy on the server.
     */
    public abstract void deleteListing(Listing listingToDelete);

}
