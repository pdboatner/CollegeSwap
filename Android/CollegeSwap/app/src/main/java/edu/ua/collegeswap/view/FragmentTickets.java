package edu.ua.collegeswap.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Fragment to display a list of Ticket listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class FragmentTickets extends SectionFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment_tickets, container, false);

        //TODO receive the ticket from the bundle
        //TODO update the ticket from the server

        Ticket exampleTicket = new Ticket();

        Button buttonTicket = (Button) view.findViewById(R.id.buttonTicketSimulate);
        buttonTicket.setOnClickListener(this);
        buttonTicket.setTag(exampleTicket); // Store the Ticket object as a tag in the View for retrieval when clicked

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Ticket) {
            // Retrieve the Ticket stored in this View
            Ticket ticket = (Ticket) v.getTag();

            callbacks.onListingClicked(ticket);
        }
    }
}
