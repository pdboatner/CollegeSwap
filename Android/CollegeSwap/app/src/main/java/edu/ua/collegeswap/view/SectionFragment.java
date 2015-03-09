package edu.ua.collegeswap.view;

import android.app.Activity;
import android.support.v4.app.Fragment;

import edu.ua.collegeswap.viewModel.Listing;

/**
 * Sections are shown by the MainDrawerActivity.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class SectionFragment extends Fragment {
    protected int sectionNumber;
    protected ListingCallbacks callbacks;

    public SectionFragment() {
        // retain this fragment. Needed when the device rotates (and maybe when phone calls are received, etc.)
        setRetainInstance(true);
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static SectionFragment newInstance(int sectionNumber) {
        SectionFragment frag;

        switch (sectionNumber) {
            case 1:
                frag = new FragmentTextbooks();
                break;
            case 2:
                frag = new FragmentTickets();
                break;
            case 3:
                frag = new FragmentSubleases();
                break;
            default:
                frag = new FragmentTextbooks();
                break;
        }

        frag.sectionNumber = sectionNumber;
        return frag;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ListingCallbacks) {
            /*
            This is responsible for initially getting the callback reference.
            We also lose the reference after rotation, so restore it.
            Otherwise, we might be updating an old reference, causing an exception.
             */
            callbacks = (ListingCallbacks) activity;
        }

        ((MainDrawerActivity) activity).onSectionAttached(sectionNumber);
    }

    /**
     * Let the parent activity implement this to handle when Listing Views are clicked. Android
     * guidelines say that Fragments don't need to do things like starting new activities, so
     * we let the parent activity do it.
     */
    public static interface ListingCallbacks {

        void onListingClicked(Listing listing);
    }

}
