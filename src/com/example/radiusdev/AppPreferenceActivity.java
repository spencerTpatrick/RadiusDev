package com.example.radiusdev;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferenceActivity{
	public static final String KEY_PREFS_USER_ID = "user_id";
    private static final String APP_SHARED_PREFS = AppPreferenceActivity.class.getSimpleName(); //  Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public AppPreferenceActivity(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getSmsBody() {
        return _sharedPrefs.getString(KEY_PREFS_USER_ID, "");
    }

    public void saveUserID(String text) {
        _prefsEditor.putString(KEY_PREFS_USER_ID, text);
        _prefsEditor.commit();
    }
}
