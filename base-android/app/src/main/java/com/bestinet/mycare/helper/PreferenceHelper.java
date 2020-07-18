package com.bestinet.mycare.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
    private static SharedPreferences preferences;

    private PreferenceHelper() {
    }

    static void savePreference(Context context, String key, String value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor currentEditor = preferences.edit();
        currentEditor.putString(key, value);
        currentEditor.apply();
    }

    static void deletePreference(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor currentEditor = preferences.edit();
        currentEditor.remove(key);
        currentEditor.apply();
    }

    static String getPreference(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }
}
