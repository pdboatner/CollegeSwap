package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.viewModel.Listing;

public class MainDrawerActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        SectionFragment.ListingCallbacks {

    // --- UI References ---
    private SectionFragment currentFragment;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    // --- State representation ---
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    /**
     * When starting an Activity, this is the value of the "extra" Object sent to that Activity.
     */
    public static final String detailListingExtra = "detailListingExtra";

    /**
     * Request code for starting activities which may update listings. When these activities
     * return,
     * we may need to reload the listings.
     */
    public static final int UPDATE_LISTING_REQUEST = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Load or restore the first initial fragment
        FragmentManager fm = getSupportFragmentManager();
        currentFragment = (SectionFragment) fm.findFragmentByTag("sectionFragment");

        if (currentFragment == null) {
            // Create it for the first time
            loadNewFragment(0);
        }
    }

    /**
     * Initialize and load a new fragment.
     */
    private void loadNewFragment(int navDrawerPosition) {
        currentFragment = SectionFragment.newInstance(navDrawerPosition + 1);

        // Update the main content by replacing fragments
        // (tag string allows us to restore it when the device rotates)
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, currentFragment, "sectionFragment")
                .commit();
    }

    @Override
    public void onListingClicked(Listing listing) {
        // Launch a new detailed Listing activity
        Intent intent = new Intent(this, listing.getDetailActivityClass());
        intent.putExtra(detailListingExtra, listing);
        startActivityForResult(intent, UPDATE_LISTING_REQUEST);
    }

    @Override
    public void launchNewListingActivity(Class newListingActivityClass) {
        // Launch an Activity to create a new Listing
        Intent intent = new Intent(this, newListingActivityClass);
        startActivityForResult(intent, UPDATE_LISTING_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If an Activity was launched that might update listings, go ahead and refresh them
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_LISTING_REQUEST && currentFragment instanceof SectionFragment.ListingSectionFragment) {
            ((SectionFragment.ListingSectionFragment) currentFragment).reloadView();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        loadNewFragment(position);
    }

    public void onSectionAttached(int number) {
        // Set up the title according to the drawer position
        mTitle = getResources().getStringArray(R.array.sections_array)[number - 1];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
