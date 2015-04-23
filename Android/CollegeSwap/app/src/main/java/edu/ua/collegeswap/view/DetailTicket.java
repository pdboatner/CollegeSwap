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
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Shows the details of a single Ticket.
 */
public class DetailTicket extends AuthenticatedActivity implements View.OnClickListener {

    private Ticket ticket;

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

        // Retrieve the Ticket Object from the Intent
        Intent intent = getIntent();
        Serializable ticketObject = intent.getSerializableExtra(MainDrawerActivity.detailListingExtra);

        if (ticketObject != null && ticketObject instanceof Ticket) {
            ticket = (Ticket) ticketObject;
        }

        // Set up the Views
        setContentView(R.layout.activity_detail_ticket);

        // Use the fields of the ticket to set TextViews and such
        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText(ticket.getTitle());

        TextView price = (TextView) findViewById(R.id.textViewPrice);
        price.setText(ticket.getAskingPriceDollars());

        TextView seller = (TextView) findViewById(R.id.textViewSeller);
        seller.setText(ticket.getPosterAccount().getName());

        TextView game = (TextView) findViewById(R.id.textViewGame);
        game.setText(ticket.getGame());

        TextView bowl = (TextView) findViewById(R.id.textViewBowl);
        bowl.setText(ticket.getBowl());

        TextView details = (TextView) findViewById(R.id.textViewDetails);
        details.setText(ticket.getDetails());

        // Set up the offer button
        findViewById(R.id.buttonMakeOffer).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_ticket, menu);

        MenuItem editButton = menu.findItem(R.id.action_edit);
        if (ticket.getPosterAccount().getName().equals(account.getName())) {
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

                Intent intent = new Intent(this, EditTicketActivity.class);
                intent.putExtra(EditTicketActivity.EXTRA_TICKET, ticket);
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

            new TransactionManager().makeOffer(account.getName(), ticket, offerText);

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
