package com.jonerds.ballanweather.data.prefs;

import android.content.SharedPreferences;

import com.jonerds.ballanweather.utils.AppConstants;

import javax.inject.Inject;

public class AppPrefsHelper implements PrefsHelper {

    SharedPreferences mSharedPreferences;

    SharedPreferences.Editor mEditor;

    @Inject
    public AppPrefsHelper(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        mSharedPreferences = sharedPreferences;
        mEditor = editor;
    }

    @Override
    public String getLastOpenedCity() {
        return mSharedPreferences.getString(AppConstants.LAST_OPENED_CITY, null);
    }

    @Override
    public boolean setLastOpenedCity(String cityName) {
        mEditor.putString(AppConstants.LAST_OPENED_CITY, cityName);
        return mEditor.commit();
    }
}
