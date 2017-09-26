package com.coursion.currencyxchange_android.background;

import android.util.Log;

import com.coursion.currencyxchange_android.api.ApiClient;
import com.coursion.currencyxchange_android.api.ApiInterface;
import com.coursion.currencyxchange_android.event.CurrencyEvent;
import com.coursion.currencyxchange_android.event.GoldEvent;
import com.coursion.currencyxchange_android.model.Currency;
import com.coursion.currencyxchange_android.model.Gold;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Boom on 9/24/2017.
 */

public class GoldTask {
    public void getGoldData() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Gold>> call =
                apiService.getGold();

        call.enqueue(new Callback<List<Gold>>() {
            @Override
            public void onResponse(Call<List<Gold>> call, Response<List<Gold>> response) {
                List<Gold> goldList = response.body();
                if (goldList.size() > 0) { // Validate the data which is not empty
                    // Send the goldList via EventBus
                    EventBus.getDefault().post(new GoldEvent(goldList));
                }
            }

            @Override
            public void onFailure(Call<List<Gold>> call, Throwable t) {
                Log.e("MyApp", "getDataCurrency ERROR :" + t.toString());
            }
        });


    }
}
