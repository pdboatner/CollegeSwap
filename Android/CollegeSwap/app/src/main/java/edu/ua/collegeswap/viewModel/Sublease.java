package edu.ua.collegeswap.viewModel;

import edu.ua.collegeswap.view.DetailSublease;

/**
 * Created by Patrick on 3/4/2015.
 */
public class Sublease extends Listing {
    @Override
    public Class getDetailActivityClass() {
        return DetailSublease.class;
    }
}
