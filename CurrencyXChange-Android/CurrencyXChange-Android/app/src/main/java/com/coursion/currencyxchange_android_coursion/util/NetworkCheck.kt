package com.coursion.currencyxchange_android_coursion.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

class NetworkCheck//Constructor
(private val context: Context) {
    val isNetworkConnected: Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}
