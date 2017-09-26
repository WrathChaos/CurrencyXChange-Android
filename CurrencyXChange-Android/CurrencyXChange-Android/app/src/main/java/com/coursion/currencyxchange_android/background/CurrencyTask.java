package com.coursion.currencyxchange_android.background;

import android.util.Log;
import com.coursion.currencyxchange_android.api.ApiClient;
import com.coursion.currencyxchange_android.api.ApiInterface;
import com.coursion.currencyxchange_android.event.CurrencyEvent;
import com.coursion.currencyxchange_android.model.Currency;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

public class CurrencyTask {
    public void getCurrencyData(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Currency>> call =
                apiService.getCurrency();

        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                List<Currency> currencyList = response.body();
                if (currencyList.size() > 0 ){ // Validate the data which is not empty
                    // Send the currencyList via EventBus
                    EventBus.getDefault().post(new CurrencyEvent(currencyList));
                }
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {
                Log.e("MyApp", "getDataCurrency ERROR :" + t.toString());
            }
        });
    }
}
