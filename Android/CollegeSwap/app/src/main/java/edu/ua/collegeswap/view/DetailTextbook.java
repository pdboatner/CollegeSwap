package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Shows the details of a single Textbook.
 */
public class DetailTextbook extends ActionBarActivity {

    private Textbook textbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        When the user clicks the Up button (left of navigation bar), this will cause us to
        receive a call to onOptionsItemSelected().
        See http://developer.android.com/training/implementing-navigation/ancestral.html
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Retrieve the Textbook Object from the Intent
        Intent intent = getIntent();
        Serializable textbookObject = intent.getSerializableExtra(MainDrawerActivity.detailListingExtra);

        if (textbookObject != null && textbookObject instanceof Textbook) {
            textbook = (Textbook) textbookObject;
        }

        // Set up the Views
        setContentView(R.layout.activity_detail_textbook);

        //TODO Use the fields of the textbook to set TextViews and such
//        TextView textView = (TextView) findViewById(R.id.example);
//        textView.setText(textbook.getSomeString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_textbook, menu);
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
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
