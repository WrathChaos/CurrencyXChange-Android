package com.coursion.currencyxchange_android_coursion.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

object ApiClient {
    val BASE_URL = "http://www.doviz.com/"
    lateinit private var retrofit: Retrofit
    val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            return retrofit
        }
}
