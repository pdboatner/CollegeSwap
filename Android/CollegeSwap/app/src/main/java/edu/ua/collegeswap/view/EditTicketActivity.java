package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.TicketAccessor;
import edu.ua.collegeswap.viewModel.Ticket;

public class EditTicketActivity extends EditListingActivity {

    // State representation
    private Ticket ticket;
    // If this Activity is editing an existing Ticket. False means a new Ticket is being created.
    private boolean editingExisting;

    // UI references
    private EditText title, price, details;
    private Spinner game, bowl;

    public final static String EXTRA_TICKET = "edu.ua.collegeswap.editticket.TICKET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the UI
        setupActionBar();
        setContentView(R.layout.activity_edit_ticket);

        // Get the UI references
        title = (EditText) findViewById(R.id.editTextTitle);
        price = (EditText) findViewById(R.id.editTextPrice);
        details = (EditText) findViewById(R.id.editTextDetails);
        game = (Spinner) findViewById(R.id.spinnerGame);
        bowl = (Spinner) findViewById(R.id.spinnerBowl);

        // Populate the spinners
        List<String> games = new TicketAccessor().getGames();
        games.add(0, "Choose game");
        ArrayAdapter<String> gameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, games);
        gameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> bowlOptions = new TicketAccessor().getBowls();
        bowlOptions.add(0, "Choose bowl");
        ArrayAdapter<String> bowlAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bowlOptions);
        bowlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        game.setAdapter(gameAdapter);
        bowl.setAdapter(bowlAdapter);

        // Try to receive the Ticket object
        Intent intent = getIntent();

        Serializable ticketObject = null;
        if (intent.hasExtra(EXTRA_TICKET)) {
            ticketObject = intent.getSerializableExtra(EXTRA_TICKET);
        }

        if (ticketObject != null && ticketObject instanceof Ticket) {
            // This Activity was launched with a Ticket. Edit that ticket.
            ticket = (Ticket) ticketObject;

            editingExisting = true;

            // Set up the input fields
            title.setText(ticket.getTitle());
            price.setText(ticket.getAskingPriceFormatted());
            details.setText(ticket.getDetails());

            // Set the spinners to the correct choice
            int gameIndex = games.indexOf(ticket.getGame());
            if (gameIndex != -1) {
                game.setSelection(gameIndex);
            }

            int bowlIndex = bowlOptions.indexOf(ticket.getBowl());
            if (bowlIndex != -1) {
                bowl.setSelection(bowlIndex);
            }

        } else {
            // This Activity was launched without a Ticket, so create a new one.
            editingExisting = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // This isn't needed- we're doing a custom action bar in setupActionBar().
//        getMenuInflater().inflate(R.menu.menu_edit_ticket, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_cancel:
                finish();
                break;
            case R.id.actionbar_done:
                //TODO Save the Ticket. Submit it to the server.

                break;
        }
    }
}
