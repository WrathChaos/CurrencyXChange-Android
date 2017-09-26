package com.coursion.currencyxchange_android.api;

import com.coursion.currencyxchange_android.model.Currency;
import com.coursion.currencyxchange_android.model.Gold;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

public interface ApiInterface {
    /**
     * Get all currency data from the api
     */
    @GET("api/v1/currencies/all/latest")
    Call<List<Currency>> getCurrency();
    /**
     * Get all gold data from the api
     */
    @GET("api/v1/golds/all/latest")
    Call<List<Gold>> getGold();
}
