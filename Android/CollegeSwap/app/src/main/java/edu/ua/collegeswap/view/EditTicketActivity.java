package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.TicketAccessor;
import edu.ua.collegeswap.database.TicketWriter;
import edu.ua.collegeswap.viewModel.Ticket;

public class EditTicketActivity extends EditListingActivity {

    // State representation
    private Ticket ticket;

    // UI references
    private EditText title, price, details;
    private Spinner game, bowl;

    public final static String EXTRA_TICKET = "edu.ua.collegeswap.editticket.TICKET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogin();

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
            ticket = new Ticket();
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
                // Save the Ticket. Submit it to the server.

                // Check the indices of the spinners. Should not be 0 - the hint.
                if (game.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please choose a game.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bowl.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please choose a bowl.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ticket.setTitle(title.getText().toString());
                try {
                    ticket.setAskingPrice(Float.parseFloat(price.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Please enter a valid price.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ticket.setDetails(details.getText().toString());
                ticket.setBowl(bowl.getSelectedItem().toString());
                ticket.setGame(game.getSelectedItem().toString());
                ticket.setPosterAccount(account);

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        TicketWriter ticketWriter = new TicketWriter();
                        if (editingExisting) {
                            ticketWriter.updateExisting(ticket);
                        } else {
                            ticketWriter.saveNew(ticket);
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        if (editingExisting) {
                            Toast.makeText(getApplicationContext(), "Updated existing ticket", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Saved new ticket", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();


                finish();

                break;
        }
    }
}
