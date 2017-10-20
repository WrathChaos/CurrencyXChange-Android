package com.coursion.currencyxchange_android.api

import com.coursion.currencyxchange_android.model.Currency
import com.coursion.currencyxchange_android.model.Gold

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

interface ApiInterface {
    /**
     * Get all currency data from the api
     */
    @get:GET("api/v1/currencies/all/latest")
    val currency: Call<List<Currency>>
    /**
     * Get all gold data from the api
     */
    @get:GET("api/v1/golds/all/latest")
    val gold: Call<List<Gold>>
}
