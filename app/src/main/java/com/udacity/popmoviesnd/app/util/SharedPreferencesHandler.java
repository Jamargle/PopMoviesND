package com.udacity.popmoviesnd.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class SharedPreferencesHandler {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHandler(final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getWayToOrderMovies(
            final String key,
            final String defaultValue) {

        return getString(key, defaultValue);
    }

    private int getInteger(
            final String keySortingModel,
            final int defaultValue) {

        return sharedPreferences.getInt(keySortingModel, defaultValue);
    }

    private String getString(
            final String keySortingModel,
            final String defaultValue) {

        return sharedPreferences.getString(keySortingModel, defaultValue);
    }

}
