package com.coursion.currencyxchange_android_coursion.model

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

data class Gold
(
        var name: String?,
        var source_name: String?,
        var source_full_name: String?,
        var full_name: String?,
        var short_name: String?,
        var buying: Double?,
        var selling: Double?,
        var change_rate: Double?,
        var update_date: Long?,
        var source: String?
)
