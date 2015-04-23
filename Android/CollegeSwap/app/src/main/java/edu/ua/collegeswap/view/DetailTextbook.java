package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.TransactionManager;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Shows the details of a single Textbook.
 */
public class DetailTextbook extends AuthenticatedActivity implements View.OnClickListener {

    private Textbook textbook;
//button1 = (Button) findVeiwById(R.id.button);

    /*button1.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Toast myToast = Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG);
            myToast.show();
        }

    });
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogin();

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

        // Use the fields of the textbook to set TextViews and such

        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText(textbook.getTitle());

        TextView price = (TextView) findViewById(R.id.textViewPrice);
        price.setText(textbook.getAskingPriceDollars());

        TextView seller = (TextView) findViewById(R.id.textViewSeller);
        seller.setText(textbook.getPosterAccount().getName());

        TextView course = (TextView) findViewById(R.id.textViewCourse);
        course.setText(textbook.getSubjectAndNumber());

        TextView details = (TextView) findViewById(R.id.textViewDetails);
        details.setText(textbook.getDetails());

        // Set up the offer button
        findViewById(R.id.buttonMakeOffer).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_textbook, menu);

        MenuItem editButton = menu.findItem(R.id.action_edit);
        if (textbook.getPosterAccount().getName().equals(account.getName())) {
            //Only show the edit button if the user is allowed to edit the Listing
            editButton.setVisible(true);
        }

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
                // Launch an activity to edit the Listing.

                Intent intent = new Intent(this, EditTextbookActivity.class);
                intent.putExtra(EditTextbookActivity.EXTRA_TEXTBOOK, textbook);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonMakeOffer) {
            String offerText = ((EditText) findViewById(R.id.editTextOffer)).getText().toString();

            Toast.makeText(this, "Sending offer \"" + offerText + "\"", Toast.LENGTH_LONG).show();

            new TransactionManager().makeOffer(account.getName(), textbook, offerText);

            // Clear the input
            EditText input = (EditText) findViewById(R.id.editTextOffer);
            input.setText("");
            input.setFocusable(false);
            hideKeyboard(this, v);
            input.setHint("(offer sent)");
            v.setEnabled(false);
        }
    }
}
