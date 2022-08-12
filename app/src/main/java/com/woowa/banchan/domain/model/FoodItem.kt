package com.woowa.banchan.domain.model

import com.google.gson.annotations.SerializedName

data class FoodItem(
    val description: String,
    val detailHash: String,
    val image: String,
    val nPrice: Long?,
    val sPrice: Long,
    val percent: Int?,
    val title: String
)
