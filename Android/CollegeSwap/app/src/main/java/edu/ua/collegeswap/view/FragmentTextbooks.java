package edu.ua.collegeswap.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.TextbookAccessor;
import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Textbook;

/**
 * Fragment to display a list of Textbook listings.
 * <p/>
 * Created by Patrick on 3/4/2015.
 */
public class FragmentTextbooks extends SectionFragment implements View.OnClickListener {

    private List<Textbook> textbooks;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment_textbooks, container, false);

        updateTextbooksFromServer();

        // Get a reference to the linear layout which will hold the View for each textbook
        LinearLayout linearLayoutTextbooks = (LinearLayout) view.findViewById(R.id.linearLayoutTextbooks);

        for (Textbook t : textbooks) {
            // Inflate the individual textbook View and put it inside the parent LinearLayout
            // (this returns the parent linearLayoutTextbooks, not the individual child View we need)
            inflater.inflate(R.layout.individual_textbook, linearLayoutTextbooks);

            // Get a reference to the individual bookmark view we just inflated
            LinearLayout textbookView = (LinearLayout) linearLayoutTextbooks.getChildAt(
                    linearLayoutTextbooks.getChildCount() - 1);

            textbookView.setTag(t); // Store the Textbook object as a tag in the View for retrieval when clicked
            textbookView.setOnClickListener(this); // allow the user to click on the whole Textbook representation

            // Find the TextViews and their their text from the Textbook fields

            TextView title = (TextView) textbookView.findViewById(R.id.textViewTitle);
            title.setText(t.getTitle());

            TextView course = (TextView) textbookView.findViewById(R.id.textViewCourse);
            course.setText(t.getSubjectAndNumber());

            TextView price = (TextView) textbookView.findViewById(R.id.textViewPrice);
            price.setText("$" + t.getAskingPrice());
        }

        return view;
    }

    private void updateTextbooksFromServer() {
        // Retrieve the list of textbooks from the server
        TextbookAccessor accessor = new TextbookAccessor();
        List<Listing> listings = accessor.getAll();
        textbooks = new ArrayList<>();
        for (Listing l : listings) {
            if (l instanceof Textbook) {
                textbooks.add((Textbook) l);
            } else {
                throw new IllegalStateException("Expected a Textbook, but found another class.");
            }
        }
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
