package edu.ua.collegeswap.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import edu.ua.collegeswap.database.AccountAccessor;
import edu.ua.collegeswap.viewModel.Account;
import edu.ua.collegeswap.viewModel.Listing;

/**
 * Sections are shown by the MainDrawerActivity.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class SectionFragment extends Fragment {
    protected int sectionNumber;
    protected ListingCallbacks callbacks;
    protected Account account;

    public static final int textbookNumber = 1;
    public static final int ticketNumber = 2;
    public static final int subleaseNumber = 3;


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
            case 4:
                frag = new FragmentProfile();
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
     * Check the login. Update the account object.
     * <p/>
     * See duplicate in SectionFragment.
     *
     * @return true if the user is logged in, false if the user is not
     */
    protected boolean checkLogin() {
        AccountAccessor accountAccessor = new AccountAccessor();
        account = accountAccessor.getCachedLogin(getActivity());
        if (account == null) {
            Toast.makeText(getActivity(), "Please log in.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

            return false;
        }
        return true;
    }

    /**
     * Let the parent activity implement this to handle when Listing Views are clicked. Android
     * guidelines say that Fragments don't need to do things like starting new activities, so
     * we let the parent activity do it.
     */
    public static interface ListingCallbacks {

        void onListingClicked(Listing listing);

        void launchNewListingActivity(Class newListingActivityClass);
    }

    public interface ListingSectionFragment {
        /**
         * Reload this fragment's view.
         */
        public void reloadView();

        /**
         * Call this before rendering to only show listings for the given user.
         */
        public void onlyShowForUser();
    }


}
