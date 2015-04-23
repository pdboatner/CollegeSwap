package edu.ua.collegeswap.view;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ua.collegeswap.R;

/**
 * Abstract class for EditTicket, EditSublease, EditTextbook, etc.
 * <p/>
 * Created by Patrick on 4/9/2015.
 */
public abstract class EditListingActivity extends AuthenticatedActivity implements View.OnClickListener {

    // If this Activity is editing an existing Listing. False means a new Listing is being created.
    protected boolean editingExisting;

    /**
     * See https://plus.google.com/+RomanNurik/posts/R49wVvcDoEW and https://android.googlesource.com/platform/developers/samples/android/+/master/ui/actionbar/DoneBar
     */
    protected void setupActionBar() {
        final LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(R.layout.actionbar_done_cancel, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(this);
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(this);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public abstract void onClick(View v);
}
