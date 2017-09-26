package com.coursion.currencyxchange_android.pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.coursion.currencyxchange_android.model.Currency;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Kuray(FreakyCoder) on 26/09/2017.
 */

public class PrefManager {

    private Context context;
    private Activity activity;

    // Constructor
    public PrefManager(Activity activity) {
        this.activity = activity;
    }

    public PrefManager(Context context) {
        this.context = context;
    }

    private SharedPreferences checkContext() {
        SharedPreferences prefs = null;
        if (activity != null)
            prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        else if (context != null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs;
    }

    public void saveSelectedCurrency(Object currency) {
        SharedPreferences prefs = checkContext();
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currency);
        editor.putString("selected_currency", json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public Currency getSelectedCurrency() {  // Key : selected_currency
        SharedPreferences prefs = checkContext();
        Gson gson = new Gson();
        String json = prefs.getString("selected_currency", null);
        Type type = new TypeToken<Currency>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
