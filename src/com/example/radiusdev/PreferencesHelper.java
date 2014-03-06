package com.example.radiusdev;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesHelper {

    public static final String KEY_PREFS_SMS_BODY = "some_string";
    private static final String APP_SHARED_PREFS = PreferencesHelper.class.getSimpleName(); //  Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public PreferencesHelper(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getSomeString() {
        return _sharedPrefs.getString(KEY_PREFS_SMS_BODY, "");
    }

    public void saveSomeString(String text) {
        _prefsEditor.putString(KEY_PREFS_SMS_BODY, text);
        _prefsEditor.commit();
    }
}
