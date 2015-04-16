package edu.ua.collegeswap.database;

import edu.ua.collegeswap.viewModel.Account;

/**
 * Created by justinkerber on 3/26/15.
 */
public class AccountAccessor {

    public Account getLogin(String s, String p) {
        //TODO get the login information for the user
        return null;
    }

    /**
     * Use a SharedPreferences to try to get the stored login info.
     *
     * @return the Account (if the user has it stored in shared preferences) or null otherwise
     */
    public Account getCachedLogin() {
        //TODO
        return null;
    }
}
