package com.coursion.currencyxchange_android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

public class NetworkCheck {
    private Context context;
    //Constructor
    public NetworkCheck(Context context) {
        this.context = context;
    }
    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
