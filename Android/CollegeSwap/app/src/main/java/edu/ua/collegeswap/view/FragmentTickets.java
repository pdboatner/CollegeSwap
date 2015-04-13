package edu.ua.collegeswap.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
public class FragmentTickets extends SectionFragment implements View.OnClickListener {

    private List<Ticket> tickets;

    public FragmentTickets() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment_tickets, container, false);

        updateTicketsFromServer();

        // Get a reference to the linear layout which will hold the View for each ticket
        LinearLayout linearLayoutTickets = (LinearLayout) view.findViewById(R.id.linearLayoutTickets);

        for (Ticket t : tickets) {
            // Inflate the individual ticket View and put it inside the parent LinearLayout
            // (this returns the parent linearLayoutTickets, not the individual child View we need)
            inflater.inflate(R.layout.individual_ticket, linearLayoutTickets);

            // Get a reference to the individual bookmark view we just inflated
            LinearLayout ticketView = (LinearLayout) linearLayoutTickets.getChildAt(
                    linearLayoutTickets.getChildCount() - 1);

            ticketView.setTag(t); // Store the Ticket object as a tag in the View for retrieval when clicked
            ticketView.setOnClickListener(this); // allow the user to click on the whole Ticket representation

            // Find the TextViews and their their text from the Ticket fields

            TextView title = (TextView) ticketView.findViewById(R.id.AptName);
            title.setText(t.getTitle());

            TextView game = (TextView) ticketView.findViewById(R.id.textViewGame);
            game.setText(t.getGame());

            TextView bowl = (TextView) ticketView.findViewById(R.id.textViewBowl);
            bowl.setText(t.getBowl());

            TextView price = (TextView) ticketView.findViewById(R.id.textPriceLabel);
            price.setText("$" + t.getAskingPrice());
        }

        return view;
    }

    private void updateTicketsFromServer() {
        // Retrieve the list of tickets from the server
        TicketAccessor accessor = new TicketAccessor();
        List<Listing> listings = accessor.getAll();
        tickets = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Ticket) {
                tickets.add((Ticket) l);
            } else {
                throw new IllegalStateException("Expected a Ticket, but found another class.");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Ticket) {
            // Retrieve the Ticket stored in this View
            Ticket ticket = (Ticket) v.getTag();

            callbacks.onListingClicked(ticket);
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
            //TODO Open the activity to create a new Ticket

            Toast.makeText(getActivity(), "Making a new Ticket", Toast.LENGTH_SHORT).show();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
