package com.vedruna.appletterboxdproyectofinal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class TokenManager {
    private static TokenManager instance = null;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static final String TAG = "TokenManager";

    private TokenManager(Context context) {
        prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context);
        }
        return instance;
    }

    public void saveToken(String token) {
        Log.d(TAG, "Saving token: " + token);
        editor.putString(Constants.PREFS_KEY_AUTH_TOKEN, token);
        editor.apply();
        Log.d(TAG, "Saved token: " + getToken());
    }

    public String getToken() {
        return prefs.getString(Constants.PREFS_KEY_AUTH_TOKEN, null);
    }

    public void clearToken() {
        editor.remove(Constants.PREFS_KEY_AUTH_TOKEN);
        editor.apply();
    }

    public void setTestToken(String token) {
        saveToken(token);
    }
}
