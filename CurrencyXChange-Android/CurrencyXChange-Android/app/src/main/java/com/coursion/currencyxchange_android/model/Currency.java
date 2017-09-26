package com.coursion.currencyxchange_android.model;

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

public class Currency {
    // Schema
    private String name;
    private String full_name;
    private String code;
    private Double buying;
    private Double selling;
    private Double update_date;
    private Double change_rate;
    private Integer currency;

    // Constructor
    public Currency(String name, String full_name, String code, Double buying, Double selling, Double update_date, Double change_rate, Integer currency) {
        this.name = name;
        this.full_name = full_name;
        this.code = code;
        this.buying = buying;
        this.selling = selling;
        this.update_date = update_date;
        this.change_rate = change_rate;
        this.currency = currency;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getBuying() {
        return buying;
    }

    public void setBuying(Double buying) {
        this.buying = buying;
    }

    public Double getSelling() {
        return selling;
    }

    public void setSelling(Double selling) {
        this.selling = selling;
    }

    public Double getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Double update_date) {
        this.update_date = update_date;
    }

    public Double getChange_rate() {
        return change_rate;
    }

    public void setChange_rate(Double change_rate) {
        this.change_rate = change_rate;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
}
