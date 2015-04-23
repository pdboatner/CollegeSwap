package edu.ua.collegeswap.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.TicketAccessor;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Fragment to display a list of Ticket listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class FragmentTickets extends SectionFragment implements View.OnClickListener, SectionFragment.ListingSectionFragment {

//    private List<Ticket> tickets;

    // UI References
    private EditText editTextMinPrice, editTextMaxPrice;
    private Spinner game, bowl;

    // State representation
    protected boolean filterByPrice = false;
    protected float minFilterPrice, maxFilterPrice;
    protected boolean filterByGame = false;
    protected String filterGame, filterBowl;
    protected boolean onlyShowForUser = false;

    public FragmentTickets() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        checkLogin();

        final View view = inflater.inflate(R.layout.section_fragment_tickets, container, false);
        // Get a reference to the linear layout which will hold the View for each ticket
        LinearLayout linearLayoutTickets = (LinearLayout) view.findViewById(R.id.linearLayoutTickets);
        final View.OnClickListener onClickListener = this;

        if (!onlyShowForUser) {
            Button buttonClear = (Button) view.findViewById(R.id.buttonClear);
            Button buttonFilter = (Button) view.findViewById(R.id.buttonFilter);
            buttonClear.setOnClickListener(this);
            buttonFilter.setOnClickListener(this);

            editTextMinPrice = (EditText) view.findViewById(R.id.editTextMinPrice);
            editTextMaxPrice = (EditText) view.findViewById(R.id.editTextMaxPrice);
            game = (Spinner) view.findViewById(R.id.spinnerGame);
            bowl = (Spinner) view.findViewById(R.id.spinnerBowl);

            // Populate the spinners. Duplicate code from EditTicketActivity.
            List<String> games = new TicketAccessor().getGames();
            games.add(0, "Choose game");
            ArrayAdapter<String> gameAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, games);
            gameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            List<String> bowlOptions = new TicketAccessor().getBowls();
            bowlOptions.add(0, "Choose bowl");
            ArrayAdapter<String> bowlAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, bowlOptions);
            bowlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            game.setAdapter(gameAdapter);
            bowl.setAdapter(bowlAdapter);
        } else {
            // Hide the filtering controls
            view.findViewById(R.id.filterControlsTickets).setVisibility(View.GONE);

            // Remove the padding
            View wholeView = view.findViewById(R.id.linearLayoutWholeTickets);
            wholeView.setPadding(0, 0, 0, 0);
        }

        // Load the Tickets
        reloadView(inflater, linearLayoutTickets, onClickListener);

        return view;
    }

    /**
     * @param inflater            used to inflate each individual ticket layout
     * @param linearLayoutTickets holds the tickets
     * @param onClickListener     for clicking on tickets
     */
    private void reloadView(final LayoutInflater inflater, final LinearLayout linearLayoutTickets, final View.OnClickListener onClickListener) {

        new AsyncTask<Void, Void, List<Ticket>>() {

            @Override
            protected List<Ticket> doInBackground(Void... params) {
                return updateTicketsFromServer();
            }

            @Override
            protected void onPostExecute(List<Ticket> tickets) {
                linearLayoutTickets.removeAllViews(); // Remove the child views, since this can be called again after filtering

                for (Ticket t : tickets) {
                    // Inflate the individual ticket View and put it inside the parent LinearLayout
                    // (this returns the parent linearLayoutTickets, not the individual child View we need)
                    inflater.inflate(R.layout.individual_ticket, linearLayoutTickets);

                    // Get a reference to the individual bookmark view we just inflated
                    LinearLayout ticketView = (LinearLayout) linearLayoutTickets.getChildAt(
                            linearLayoutTickets.getChildCount() - 1);

                    ticketView.setTag(t); // Store the Ticket object as a tag in the View for retrieval when clicked
                    ticketView.setOnClickListener(onClickListener); // allow the user to click on the whole Ticket representation

                    // Find the TextViews and their their text from the Ticket fields

                    TextView title = (TextView) ticketView.findViewById(R.id.textViewTitle);
                    title.setText(t.getTitle());

                    TextView game = (TextView) ticketView.findViewById(R.id.textViewGame);
                    game.setText(t.getGame());

                    TextView bowl = (TextView) ticketView.findViewById(R.id.textViewBowl);
                    bowl.setText(t.getBowl());

                    TextView price = (TextView) ticketView.findViewById(R.id.textViewPrice);
                    price.setText(t.getAskingPriceDollars());
                }
            }
        }.execute();
    }

    private List<Ticket> updateTicketsFromServer() {
        // Retrieve the list of tickets from the server
        TicketAccessor accessor = new TicketAccessor();
        List<Listing> listings;

        if (onlyShowForUser) {
            listings = accessor.getByUser(account);
        } else {
            if (filterByPrice) {
                if (filterByGame) {
                    return accessor.get(filterGame, filterBowl, (int) minFilterPrice, (int) maxFilterPrice);
                } else {
                    listings = accessor.getByPrice((int) minFilterPrice, (int) maxFilterPrice);
                }
            } else {
                if (filterByGame) {
                    return accessor.getByGame(filterGame, filterBowl);
                } else {
                    listings = accessor.getAll();
                }
            }
        }


        List<Ticket> tickets = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Ticket) {
                tickets.add((Ticket) l);
            } else {
                throw new IllegalStateException("Expected a Ticket, but found another class.");
            }
        }

        return tickets;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonClear:
                // Clear the UI filtering inputs
                editTextMinPrice.setText("");
                editTextMaxPrice.setText("");
                game.setSelection(0);
                bowl.setSelection(0);

                // Set the state variables to show all tickets
                filterByPrice = false;
                filterByGame = false;

                reloadView();

                break;
            case R.id.buttonFilter:
                // Set the state variables to possibly filter tickets
                try {
                    minFilterPrice = Float.parseFloat(editTextMinPrice.getText().toString());
                    maxFilterPrice = Float.parseFloat(editTextMaxPrice.getText().toString());

                    filterByPrice = true;
                } catch (Exception e) {
                    filterByPrice = false;
                }

                if (game.getSelectedItemPosition() != 0) {
                    filterGame = (String) game.getSelectedItem();
                    filterByGame = true;

                    if (bowl.getSelectedItemPosition() != 0) {
                        filterBowl = (String) bowl.getSelectedItem();
                    } else {
                        filterBowl = ""; // Filter by game, but not by bowl
                    }
                } else {
                    filterByGame = false;
                }

                reloadView();

                break;
            default:
                if (v.getTag() instanceof Ticket) {
                    // Retrieve the Ticket stored in this View
                    Ticket ticket = (Ticket) v.getTag();

                    callbacks.onListingClicked(ticket);
                }
                break;
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_content_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new) {
            // Open the activity to create a new Ticket
            ((MainDrawerActivity) getActivity()).launchNewListingActivity(EditTicketActivity.class);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void reloadView() {
        reloadView(getActivity().getLayoutInflater(), (LinearLayout) getActivity().findViewById(R.id.linearLayoutTickets), this);
    }

    @Override
    public void onlyShowForUser() {
        onlyShowForUser = true;
    }
}
