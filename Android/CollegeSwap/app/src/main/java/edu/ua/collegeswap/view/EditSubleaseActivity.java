package edu.ua.collegeswap.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.SubleaseAccessor;
import edu.ua.collegeswap.database.SubleaseWriter;
import edu.ua.collegeswap.viewModel.Sublease;

public class EditSubleaseActivity extends EditListingActivity {

    // State representation
    private Sublease sublease;

    // UI References
    private EditText title, price, details;
    private Spinner location;
    private DatePicker startDate, endDate;

    public static final String EXTRA_SUBLEASE = "edu.ua.collegeswap.editticket.SUBLEASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogin();

        // Set up the UI
        setupActionBar();
        setContentView(R.layout.activity_edit_sublease);

        // Get the UI references
        title = (EditText) findViewById(R.id.editTextTitle);
        price = (EditText) findViewById(R.id.editTextPrice);
        details = (EditText) findViewById(R.id.editTextDetails);
        location = (Spinner) findViewById(R.id.spinnerLocation);
        startDate = (DatePicker) findViewById(R.id.datePickerStart);
        endDate = (DatePicker) findViewById(R.id.datePickerEnd);

        // Populate the spinner
        List<String> locations = new SubleaseAccessor().getLocations();
        locations.add(0, "Choose location");
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(locationAdapter);

        // Try to receive the Sublease object
        Intent intent = getIntent();

        Serializable subleaseObject = null;
        if (intent.hasExtra(EXTRA_SUBLEASE)) {
            subleaseObject = intent.getSerializableExtra(EXTRA_SUBLEASE);
        }

        if (subleaseObject != null && subleaseObject instanceof Sublease) {
            // This Activity was launched with a Sublease. Edit that sublease.
            sublease = (Sublease) subleaseObject;

            editingExisting = true;

            // Set up the input fields
            title.setText(sublease.getTitle());
            price.setText(sublease.getAskingPriceFormatted());
            details.setText(sublease.getDetails());

            // Set the spinner to the correct choice
            int locationIndex = locations.indexOf(sublease.getLocation());
            if (locationIndex != -1) {
                location.setSelection(locationIndex);
            }

            // Set the date pickers to the correct dates
            Calendar startCal = sublease.getStartDate();
            startDate.updateDate(
                    startCal.get(Calendar.YEAR),
                    startCal.get(Calendar.MONTH),
                    startCal.get(Calendar.DAY_OF_MONTH));

            Calendar endCal = sublease.getEndDate();
            endDate.updateDate(
                    endCal.get(Calendar.YEAR),
                    endCal.get(Calendar.MONTH),
                    endCal.get(Calendar.DAY_OF_MONTH));
        } else {
            // This Activity was launched without a Sublease, so create a new one.
            sublease = new Sublease();
            editingExisting = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // This isn't needed- we're doing a custom action bar in setupActionBar().
//        getMenuInflater().inflate(R.menu.menu_edit_sublease, menu);
        return true;
    }

    /**
     * @param year  same as Calendar.YEAR
     * @param month same as Calendar.MONTH
     * @param day   same as Calendar.DAY_OF_MONTH
     * @return a Calendar object set to noon on the given day
     */
    private Calendar getCalendar(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.clear();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        c.set(Calendar.HOUR_OF_DAY, 12);

        return c;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_cancel:
                finish();
                break;
            case R.id.actionbar_done:
                // Save the Sublease. Submit it to the server.

                // Check the indices of the spinners. Should not be 0 - the hint.
                if (location.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please choose a location", Toast.LENGTH_SHORT).show();
                    return;
                }

                sublease.setTitle(title.getText().toString());
                try {
                    sublease.setAskingPrice(Float.parseFloat(price.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Please enter a valid price.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sublease.setDetails(details.getText().toString());
                sublease.setLocation(location.getSelectedItem().toString());
                sublease.setStartDate(getCalendar(
                        startDate.getYear(),
                        startDate.getMonth(),
                        startDate.getDayOfMonth()
                ));
                sublease.setEndDate(getCalendar(
                        endDate.getYear(),
                        endDate.getMonth(),
                        endDate.getDayOfMonth()
                ));
                sublease.setPosterAccount(account);


                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        SubleaseWriter subleaseWriter = new SubleaseWriter();
                        if (editingExisting) {
                            subleaseWriter.updateExisting(sublease);
                        } else {
                            subleaseWriter.saveNew(sublease);
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        if (editingExisting) {
                            Toast.makeText(getApplicationContext(), "Updated existing sublease", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Saved new sublease", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();


                finish();

                break;
        }
    }
}
