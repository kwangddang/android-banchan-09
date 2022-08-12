package com.woowa.banchan.domain.model

data class FoodItem(
    val description: String,
    val detailHash: String,
    val image: String,
    val nPrice: Int?,
    val sPrice: Int,
    val percent: Int?,
    val title: String
)
