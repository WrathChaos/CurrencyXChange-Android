package com.coursion.currencyxchange_android.model;

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

public class Gold {

    private String name;
    private String source_name;
    private String source_full_name;
    private String full_name;
    private String short_name;
    private Double buying;
    private Double selling;
    private Double change_rate;
    private Long update_date;
    private String source;

    // Constructor
    public Gold(String name, String source_name, String source_full_name, String full_name, String short_name, Double buying, Double selling, Double change_rate, Long update_date, String source) {
        this.name = name;
        this.source_name = source_name;
        this.source_full_name = source_full_name;
        this.full_name = full_name;
        this.short_name = short_name;
        this.buying = buying;
        this.selling = selling;
        this.change_rate = change_rate;
        this.update_date = update_date;
        this.source = source;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getSource_full_name() {
        return source_full_name;
    }

    public void setSource_full_name(String source_full_name) {
        this.source_full_name = source_full_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
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

    public Double getChange_rate() {
        return change_rate;
    }

    public void setChange_rate(Double change_rate) {
        this.change_rate = change_rate;
    }

    public Long getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Long update_date) {
        this.update_date = update_date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
