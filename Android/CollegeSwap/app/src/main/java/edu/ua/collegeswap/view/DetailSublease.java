package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.viewModel.Sublease;

/**
 * Shows the details of a single Sublease.
 */
public class DetailSublease extends ActionBarActivity {

    private Sublease sublease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        When the user clicks the Up button (left of navigation bar), this will cause us to
        receive a call to onOptionsItemSelected().
        See http://developer.android.com/training/implementing-navigation/ancestral.html
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve the Sublease Object from the Intent
        Intent intent = getIntent();
        Serializable subleaseObject = intent.getSerializableExtra(MainDrawerActivity.detailListingExtra);

        if (subleaseObject != null && subleaseObject instanceof Sublease) {
            sublease = (Sublease) subleaseObject;
        }

        // Set up the Views
        setContentView(R.layout.activity_detail_sublease);

        // Use the fields of the sublease to set TextViews and such
        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText(sublease.getTitle());

        TextView price = (TextView) findViewById(R.id.textViewPrice);
        price.setText(sublease.getAskingPriceDollars());

        TextView seller = (TextView) findViewById(R.id.textViewSeller);
        seller.setText(sublease.getPosterAccount().getName());

        TextView location = (TextView) findViewById(R.id.textViewLocation);
        location.setText(sublease.getLocation());

        TextView dateRange = (TextView) findViewById(R.id.textViewDateRange);
        dateRange.setText(sublease.getDateRange());

        TextView details = (TextView) findViewById(R.id.textViewDetails);
        details.setText(sublease.getDetails());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_sublease, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                /*
                This prevents the MainDrawerActivity from reverting to the default Section number.
                This makes the Up/Home button behavior identical to the hardware Back button behavior.
                 */
                finish();
                return true;
            case R.id.action_edit:
                Toast.makeText(this, "Edit Button clicked", Toast.LENGTH_SHORT).show();

                // Launch an activity to edit the Listing.

                Intent intent = new Intent(this, EditSubleaseActivity.class);
                intent.putExtra(EditSubleaseActivity.EXTRA_SUBLEASE, sublease);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
