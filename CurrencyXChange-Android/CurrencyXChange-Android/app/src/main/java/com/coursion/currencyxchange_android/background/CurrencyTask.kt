package com.coursion.currencyxchange_android.background

import android.util.Log
import com.coursion.currencyxchange_android.api.ApiClient
import com.coursion.currencyxchange_android.api.ApiInterface
import com.coursion.currencyxchange_android.event.CurrencyEvent
import com.coursion.currencyxchange_android.model.Currency
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

class CurrencyTask {
    fun getCurrencyData() {
        val apiService = ApiClient.client.create<ApiInterface>(ApiInterface::class.java)

        val call = apiService.currency

        call.enqueue(object : Callback<List<Currency>> {
            override fun onResponse(call: Call<List<Currency>>, response: Response<List<Currency>>) {
                val currencyList = response.body()
                if (currencyList.size > 0) { // Validate the data which is not empty
                    // Send the currencyList via EventBus
                    EventBus.getDefault().post(CurrencyEvent(currencyList))
                }
            }

            override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                Log.e("MyApp", "getDataCurrency ERROR :" + t.toString())
            }
        })
    }
}
