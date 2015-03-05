package edu.ua.collegeswap.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.viewModel.Sublease;

/**
 * Fragment to display a list of Sublease listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class SectionFragmentSubleases extends SectionFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment_subleases, container, false);

        Sublease exampleSublease = new Sublease();

        Button buttonSublease = (Button) view.findViewById(R.id.buttonSubleaseSimulate);
        buttonSublease.setOnClickListener(this);
        buttonSublease.setTag(exampleSublease); // Store the Sublease object as a tag in the View for retrieval when clicked

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Sublease) {
            // Retrieve the Sublease stored in this View
            Sublease sublease = (Sublease) v.getTag();

            callbacks.onListingClicked(sublease);
        }
    }
}
