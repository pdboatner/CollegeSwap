package edu.ua.collegeswap.viewModel;

import edu.ua.collegeswap.view.DetailTicket;

/**
 * Created by Patrick on 3/4/2015.
 */
public class Ticket extends Listing {
    @Override
    public Class getDetailActivityClass() {
        return DetailTicket.class;
    }
}
