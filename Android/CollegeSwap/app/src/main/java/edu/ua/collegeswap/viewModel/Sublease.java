package edu.ua.collegeswap.viewModel;

import java.util.Calendar;

import edu.ua.collegeswap.view.DetailSublease;

/**
 * Created by Patrick on 3/4/2015.
 */
public class Sublease extends Listing {

    private Calendar startDate;
    private Calendar endDate;

    @Override
    public Class getDetailActivityClass() {
        return DetailSublease.class;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
}
