package edu.ua.collegeswap.viewModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Patrick on 3/4/2015.
 */
public abstract class Listing implements Serializable {

    private Calendar date;
    private Account posterAccount;
    private float askingPrice;
    private int ID;

    public abstract Class getDetailActivityClass();

    public List<Integer> getOffers() {
        return offers;
    }

    public void setOffers(List<Integer> offers) {
        this.offers = offers;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Account getPosterAccount() {
        return posterAccount;
    }

    public void setPosterAccount(Account posterAccount) {
        this.posterAccount = posterAccount;
    }

    public float getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(float askingPrice) {
        this.askingPrice = askingPrice;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private List<Integer> offers;
}
