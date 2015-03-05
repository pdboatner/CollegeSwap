package edu.ua.collegeswap.viewModel;

import edu.ua.collegeswap.view.DetailTextbook;

/**
 * Created by Patrick on 3/4/2015.
 */
public class Textbook extends Listing {
    @Override
    public Class getDetailActivityClass() {
        return DetailTextbook.class;
    }
}
