package edu.ua.collegeswap.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import edu.ua.collegeswap.R;
import edu.ua.collegeswap.database.SubleaseWriter;

public class EditSubleaseActivity extends EditListingActivity {

    public static final String EXTRA_SUBLEASE = "edu.ua.collegeswap.editticket.SUBLEASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the UI
        setupActionBar();
        setContentView(R.layout.activity_edit_sublease);

        //TODO Set up the input fields and their behavior


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // This isn't needed- we're doing a custom action bar in setupActionBar().
//        getMenuInflater().inflate(R.menu.menu_edit_sublease, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_cancel:
                finish();
                break;
            case R.id.actionbar_done:
                // Save the Sublease. Submit it to the server.

                //TODO check the indices of the spinners. Should not be 0 - the hint.

                SubleaseWriter subleaseWriter = new SubleaseWriter();
                if (editingExisting) {
//                    subleaseWriter.updateExisting(sublease);
                    Toast.makeText(this, "Updating existing sublease", Toast.LENGTH_SHORT).show();
                } else {
//                    subleaseWriter.saveNew(sublease);
                    Toast.makeText(this, "Saving new sublease", Toast.LENGTH_SHORT).show();
                }

                finish(); // TODO ask the calling activity to refresh now?

                break;
        }
    }
}
