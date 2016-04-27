package com.imtpmd.edogunnar.studiebarometer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Gunnar on 29-3-2016.
 */
public class SharedPreferences {

    // wijzigen sharedPreferences
    public static void saveStringToSharedPreferences(Context mContext, String key, String value) {
        if (mContext != null) {
            android.content.SharedPreferences mSharedPreferences = mContext.getSharedPreferences("SHARED_PREFERENCES", 0);
            if (mSharedPreferences != null) {
                mSharedPreferences.edit().putString(key, value).apply();
//                Toast.makeText(mContext, "sharedPreferences zijn gewijzigd!; " + key, Toast.LENGTH_LONG).show();
                Log.d("sharedPref(" + key + ") gewijzigd", value); // toont key en value on logcat
            }
        }
    }

    // lezen sharedPreferences
    public static String readStringFromSharedPreferences(Context mContext, String key) {
        if (mContext != null) {
            android.content.SharedPreferences mSharedPreferences = mContext.getSharedPreferences("SHARED_PREFERENCES", 0);
            if (mSharedPreferences != null) {
//                Toast.makeText(mContext, "sharedPreferences zijn uitgelezen!; " + key, Toast.LENGTH_SHORT).show();
                Log.d("sharedPref(" + key + ") uitgelezen", mSharedPreferences.getString(key, null)); // toont key en value on logcat
                return mSharedPreferences.getString(key, null);
            }
        }
        return null;
    }
}
