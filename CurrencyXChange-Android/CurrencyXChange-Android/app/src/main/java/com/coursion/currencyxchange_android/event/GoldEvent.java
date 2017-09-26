package com.coursion.currencyxchange_android.event;

import com.coursion.currencyxchange_android.model.Gold;

import java.util.List;

/**
 * Created by Boom on 9/24/2017.
 */

public class GoldEvent {
    private List<Gold> goldList;

    // Constructor
    public GoldEvent(List<Gold> goldList) {
        this.goldList = goldList;
    }

    // Getters and Setters
    public List<Gold> getGoldList() {
        return goldList;
    }

    public void setGoldList(List<Gold> goldList) {
        this.goldList = goldList;
    }
}
