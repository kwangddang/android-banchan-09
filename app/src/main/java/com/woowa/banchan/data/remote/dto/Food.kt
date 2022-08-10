package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("body")
    val body: List<FoodItem>,
    @SerializedName("statusCode")
    val statusCode: Int
)