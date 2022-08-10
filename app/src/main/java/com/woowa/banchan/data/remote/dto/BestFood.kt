package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName

data class BestFood(
    @SerializedName("body")
    val body: List<BestFoodCategory>,
    @SerializedName("statusCode")
    val statusCode: Int
)