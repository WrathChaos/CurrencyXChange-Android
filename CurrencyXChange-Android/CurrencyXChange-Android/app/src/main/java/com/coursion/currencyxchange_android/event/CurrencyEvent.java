package com.coursion.currencyxchange_android.event;

import com.coursion.currencyxchange_android.model.Currency;

import java.util.List;

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

public class CurrencyEvent {

    private List<Currency> currencyList;
    // Constructor
    public CurrencyEvent(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }
    // Getters and Setters
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }
}
