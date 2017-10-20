package com.coursion.currencyxchange_android.background

import android.util.Log

import com.coursion.currencyxchange_android.api.ApiClient
import com.coursion.currencyxchange_android.api.ApiInterface
import com.coursion.currencyxchange_android.event.CurrencyEvent
import com.coursion.currencyxchange_android.event.GoldEvent
import com.coursion.currencyxchange_android.model.Currency
import com.coursion.currencyxchange_android.model.Gold

import org.greenrobot.eventbus.EventBus

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Boom on 9/24/2017.
 */

class GoldTask {
    fun getGoldData() {
        val apiService = ApiClient.client.create<ApiInterface>(ApiInterface::class.java)

        val call = apiService.gold

        call.enqueue(object : Callback<List<Gold>> {
            override fun onResponse(call: Call<List<Gold>>, response: Response<List<Gold>>) {
                val goldList = response.body()
                if (goldList.size > 0) { // Validate the data which is not empty
                    // Send the goldList via EventBus
                    EventBus.getDefault().post(GoldEvent(goldList))
                }
            }

            override fun onFailure(call: Call<List<Gold>>, t: Throwable) {
                Log.e("MyApp", "getDataCurrency ERROR :" + t.toString())
            }
        })


    }
}
