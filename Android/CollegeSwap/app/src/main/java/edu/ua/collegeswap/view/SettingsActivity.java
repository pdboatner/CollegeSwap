package edu.ua.collegeswap.view;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import edu.ua.collegeswap.R;

/**
 * Created by jpkerber on 4/2/2015.
 */
public class SettingsActivity extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_general);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_username_key)));
    }


    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String str = value.toString();

        preference.setSummary(str);
        return true;
    }
}
