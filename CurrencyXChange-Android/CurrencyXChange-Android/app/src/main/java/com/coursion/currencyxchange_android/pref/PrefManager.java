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
import java.util.ArrayList;

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

    /**
     *     Save and get ArrayList in SharedPreference
     */

    public void saveColorArray(String key){
        ArrayList<String> list;
        if (key.equalsIgnoreCase("currency_color")){
            list = generateColorArray();
        } else if(key.equalsIgnoreCase("gold_color")){
            list = generateGoldColorArray();
        } else {
            list = generateColorArray();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getColorArray(String key){ // Currency Key : currency_color, Gold Key : gold_color
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private ArrayList<String> generateColorArray(){
        ArrayList<String> colorArray = new ArrayList<>();
        colorArray.add("#444C5C");   // US DOLLAR
        colorArray.add("#CE5A57");   // EURO
        colorArray.add("#78A5A3");   // UK STERLIN
        colorArray.add("#F98866");   // ISVICRE FRANGI
        colorArray.add("#90AFC5");   // KANADA DOLARI
        colorArray.add("#336B87");   // RUS RUBLESI
        colorArray.add("#80BD9E");   // BAE DIRHEMI
        colorArray.add("#89DA59");   // AUSTRALYA DOLARI
        colorArray.add("#E1B16A");   // DANIMARKA KRONU
        colorArray.add("#FF420E");   // NORVEC KRONU
        return colorArray;
    }

    public ArrayList<String> generateGoldColorArray(){
        ArrayList<String> colorArray = new ArrayList<>();
        // Gold Colors
        colorArray.add("#FFDC73");
        colorArray.add("#FFCF40");
        colorArray.add("#FFBF00");
        colorArray.add("#BF9B30");
        colorArray.add("#A67C00");
        // Silver Colors
        colorArray.add("#ACB4B7");
        colorArray.add("#4C5E62");
        colorArray.add("#62949B");
        colorArray.add("#01758C");
        colorArray.add("#B7E3E4");
        return colorArray;
    }
}
