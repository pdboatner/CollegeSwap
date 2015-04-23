package edu.ua.collegeswap.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import edu.ua.collegeswap.database.AccountAccessor;
import edu.ua.collegeswap.viewModel.Account;

/**
 * Activities where the user must be logged in. Provides methods to check this, and instruct them
 * to
 * log in if not.
 * Created by Patrick on 4/22/2015.
 */
public abstract class AuthenticatedActivity extends ActionBarActivity {

    protected Account account;

    /**
     * Check the login. Update the account object.
     * <p/>
     * See duplicate in SectionFragment.
     *
     * @return true if the user is logged in, false if the user is not
     */
    protected boolean checkLogin() {
        AccountAccessor accountAccessor = new AccountAccessor();
        account = accountAccessor.getCachedLogin(this);
        if (account == null) {
            Toast.makeText(this, "Please log in.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            return false;
        }
        return true;
    }

}
