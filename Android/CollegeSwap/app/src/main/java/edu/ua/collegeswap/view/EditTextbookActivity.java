package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.TextbookAccessor;
import edu.ua.collegeswap.database.TextbookWriter;
import edu.ua.collegeswap.viewModel.Textbook;

public class EditTextbookActivity extends EditListingActivity {

    // State representation
    private Textbook textbook;
    private boolean mayResetCourseNumber = true; // Presents the dropdown from being reset during onCreate()

    // UI References
    private EditText title, price, details;
    private Spinner courseSubject, courseNumber;

    public final static String EXTRA_TEXTBOOK = "edu.ua.collegeswap.edittextbook.TEXTBOOK";
    public final static String chooseNumberMessage = "Choose course number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the UI
        setupActionBar();
        setContentView(R.layout.activity_edit_textbook);

        // Get the UI references
        title = (EditText) findViewById(R.id.editTextTitle);
        price = (EditText) findViewById(R.id.editTextPrice);
        details = (EditText) findViewById(R.id.editTextDetails);
        courseSubject = (Spinner) findViewById(R.id.spinnerSubject);
        courseNumber = (Spinner) findViewById(R.id.spinnerNumber);

        // Populate the spinners
        final List<String> courseSubjects = new TextbookAccessor().getCourseSubjects();
        courseSubjects.add(0, "Choose course subject");
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseSubjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> courseNumbers = new ArrayList<>(); // This needs to change when subject is changed
        courseNumbers.add(0, chooseNumberMessage);
        final ArrayAdapter<String> numberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseNumbers);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        courseSubject.setAdapter(subjectAdapter);
        courseNumber.setAdapter(numberAdapter);

        // Change the courseNumbers whenever the subject is changed
        courseSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) { // ignore the "choose course subject" item
                    // Give the course number adapter the appropriate numbers for the selected subject.
                    updateCourseNumbers(courseSubjects.get(position), numberAdapter, courseNumber);

                    if (!mayResetCourseNumber) {
                        mayResetCourseNumber = true;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // Try to receive the Textbook object
        Intent intent = getIntent();

        Serializable textbookObject = null;
        if (intent.hasExtra(EXTRA_TEXTBOOK)) {
            textbookObject = intent.getSerializableExtra(EXTRA_TEXTBOOK);
        }

        if (textbookObject != null && textbookObject instanceof Textbook) {
            // This Activity was launched with a Textbook. Edit that textbook.
            textbook = (Textbook) textbookObject;

            editingExisting = true;

            // Set up the input fields
            title.setText(textbook.getTitle());
            price.setText(textbook.getAskingPriceFormatted());
            details.setText(textbook.getDetails());

            // Set the spinners to the correct choice
            int subjectIndex = courseSubjects.indexOf(textbook.getCourseSubject());
            if (subjectIndex != -1) {
                courseSubject.setSelection(subjectIndex);
            }

            // Update the course numbers for the given subject
            updateCourseNumbers(textbook.getCourseSubject(), numberAdapter, courseNumber);

            int numberIndex = courseNumbers.indexOf(textbook.getCourseNumber() + "");
            if (numberIndex != -1) {
                mayResetCourseNumber = false; // Prevent the course number from being reset
                courseNumber.setSelection(numberIndex);
            }
        } else {
            // This Activity was launched without a Textbook, so create a new one.
            editingExisting = false;
        }
    }

    private void updateCourseNumbers(String subject, ArrayAdapter<String> numberAdapter, Spinner courseNumber) {
        numberAdapter.clear();
        numberAdapter.add(chooseNumberMessage);
        numberAdapter.addAll(new TextbookAccessor().getCourseNumbersStrings(subject));
        if (mayResetCourseNumber) {
            /*
            Prevent this from resetting the course number. Sometimes the ItemSelectedListener
            is called when we don't want it. We've already set this selected,
            and we must prevent it from being cleared.
             */
            courseNumber.setSelection(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // This isn't needed- we're doing a custom action bar in setupActionBar().
//        getMenuInflater().inflate(R.menu.menu_edit_textbook, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.actionbar_cancel:
                finish();
                break;
            case R.id.actionbar_done:
                // Save the Textbook. Submit it to the server.

                textbook.setTitle(title.getText().toString());
                textbook.setAskingPrice(Float.parseFloat(price.getText().toString()));
                textbook.setDetails(details.getText().toString());

                // Check the indices of the spinners. Should not be 0 - the hint.
                if (courseSubject.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please choose a course subject.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (courseNumber.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please choose a course number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                textbook.setCourseSubject(courseSubject.getSelectedItem().toString());
                textbook.setCourseNumber(Integer.parseInt(courseNumber.getSelectedItem().toString()));

                TextbookWriter textbookWriter = new TextbookWriter();
                if (editingExisting) {
                    textbookWriter.updateExisting(textbook);
                    Toast.makeText(this, "Updating existing textbook", Toast.LENGTH_SHORT).show();
                } else {
                    textbookWriter.saveNew(textbook);
                    Toast.makeText(this, "Saving new textbook", Toast.LENGTH_SHORT).show();
                }

                finish(); // TODO ask the calling activity to refresh now?

                break;
        }
    }
}
