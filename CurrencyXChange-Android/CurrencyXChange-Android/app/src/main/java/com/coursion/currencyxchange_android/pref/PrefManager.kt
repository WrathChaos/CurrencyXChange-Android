package com.coursion.currencyxchange_android.pref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.view.View

import com.coursion.currencyxchange_android.model.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util.ArrayList

/**
 * Created by Kuray(FreakyCoder) on 26/09/2017.
 */

class PrefManager {

    private var context: Context? = null
    private var activity: Activity? = null

    // Key : selected_currency
    val selectedCurrency: Currency?
        get() {
            val prefs = checkContext()
            val gson = Gson()
            val json = prefs!!.getString("selected_currency", null)
            val type = object : TypeToken<Currency>() {

            }.type
            return gson.fromJson<Currency>(json, type)
        }

    // Constructor
    constructor(activity: Activity) {
        this.activity = activity
    }

    constructor(context: Context) {
        this.context = context
    }

    private fun checkContext(): SharedPreferences? {
        var prefs: SharedPreferences? = null
        if (activity != null)
            prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        else if (context != null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs
    }

    fun saveSelectedCurrency(currency: Any) {
        val prefs = checkContext()
        val editor = prefs!!.edit()
        val gson = Gson()
        val json = gson.toJson(currency)
        editor.putString("selected_currency", json)
        editor.apply()     // This line is IMPORTANT !!!
    }

    /**
     * Save and get ArrayList in SharedPreference
     */

    fun saveColorArray(key: String) {
        val list: ArrayList<String>
        if (key.equals("currency_color", ignoreCase = true)) {
            list = generateColorArray()
        } else if (key.equals("gold_color", ignoreCase = true)) {
            list = generateGoldColorArray()
        } else {
            list = generateColorArray()
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()     // This line is IMPORTANT !!!
    }

    fun getColorArray(key: String): ArrayList<String>? { // Currency Key : currency_color, Gold Key : gold_color
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<String>>() {

        }.type
        return gson.fromJson<ArrayList<String>>(json, type)
    }

    private fun generateColorArray(): ArrayList<String> {
        val colorArray = ArrayList<String>()
        colorArray.add("#444C5C")   // US DOLLAR
        colorArray.add("#FF7700")   // EURO
        colorArray.add("#78A5A3")   // UK STERLIN
        colorArray.add("#F98866")   // ISVICRE FRANGI
        colorArray.add("#90AFC5")   // KANADA DOLARI
        colorArray.add("#336B87")   // RUS RUBLESI
        colorArray.add("#80BD9E")   // BAE DIRHEMI
        colorArray.add("#89DA59")   // AUSTRALYA DOLARI
        colorArray.add("#E1B16A")   // DANIMARKA KRONU
        colorArray.add("#FF420E")   // NORVEC KRONU
        return colorArray
    }

    fun generateGoldColorArray(): ArrayList<String> {
        val colorArray = ArrayList<String>()
        // Gold Colors
        colorArray.add("#FFDC73")
        colorArray.add("#FFCF40")
        colorArray.add("#FFBF00")
        colorArray.add("#BF9B30")
        colorArray.add("#A67C00")
        // Silver Colors
        colorArray.add("#ACB4B7")
        colorArray.add("#4C5E62")
        colorArray.add("#62949B")
        colorArray.add("#01758C")
        colorArray.add("#B7E3E4")
        return colorArray
    }

    fun clearPreferences(){
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = prefs.edit()
        editor.clear().apply()
    }
}
