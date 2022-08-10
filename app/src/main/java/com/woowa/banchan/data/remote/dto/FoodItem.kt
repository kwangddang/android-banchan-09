package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FoodItem(
    @SerializedName("alt")
    val alt: String,
    @SerializedName("badge")
    val badge: List<String>,
    @SerializedName("delivery_type")
    val deliveryType: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("detail_hash")
    val detailHash: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("n_price")
    val nPrice: String,
    @SerializedName("s_price")
    val sPrice: String,
    @SerializedName("title")
    val title: String
)