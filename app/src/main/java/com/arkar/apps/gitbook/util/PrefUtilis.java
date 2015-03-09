package com.arkar.apps.gitbook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by arkar on 12/19/14.
 */
public class PrefUtilis {

    private static final String PREF_AUTHENTICATED = "pref_authenticated";

    private static final String PREF_OAUTH_TOKEN = "pref_oauth_token";

    public static void setAuthenticated(Context context, int status) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_AUTHENTICATED, status).commit();
    }

    public static int checkAuthenticated(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_AUTHENTICATED, 0);
    }

    public static void setOAuthToken(Context context, String token) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_OAUTH_TOKEN, token).commit();
    }

    public static String getOAuthToken(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_OAUTH_TOKEN, "");
    }
}
