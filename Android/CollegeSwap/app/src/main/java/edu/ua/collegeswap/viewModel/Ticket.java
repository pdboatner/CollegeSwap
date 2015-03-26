package edu.ua.collegeswap.viewModel;

import edu.ua.collegeswap.view.DetailTicket;

/**
 * Created by Patrick on 3/4/2015.
 */
public class Ticket extends Listing {

    private String game;
    private String bowl;

    public Ticket() {
        super();
    }

    @Override
    public Class getDetailActivityClass() {
        return DetailTicket.class;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getBowl() {
        return bowl;
    }

    public void setBowl(String bowl) {
        this.bowl = bowl;
    }


}
