package edu.ua.collegeswap.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Fragment to display a list of Textbook listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class SectionFragmentTextbooks extends SectionFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment_textbooks, container, false);

        Textbook exampleTextbook = new Textbook();

        Button buttonTextbook = (Button) view.findViewById(R.id.buttonTextbookSimulate);
        buttonTextbook.setOnClickListener(this);
        buttonTextbook.setTag(exampleTextbook); // Store the Textbook object as a tag in the View for retrieval when clicked

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Textbook) {
            // Retrieve the Textbook stored in this View
            Textbook textbook = (Textbook) v.getTag();

            callbacks.onListingClicked(textbook);
        }
    }
}
