package com.udacity.popmoviesnd.presentation.settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.udacity.popmoviesnd.R;

public final class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sorting_model_key)));
    }

    @Override
    public boolean onPreferenceChange(
            final Preference preference,
            final Object newValue) {

        if (preference instanceof ListPreference) {
            final ListPreference listPreference = (ListPreference) preference;
            final int prefIndex = listPreference.findIndexOfValue(newValue.toString());
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(newValue.toString());
        }
        return true;
    }

    private void bindPreferenceSummaryToValue(final Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(
                preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }

}
