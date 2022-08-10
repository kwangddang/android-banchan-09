package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FoodDetail(
    @SerializedName("data")
    val data: DetailItem,
    @SerializedName("hash")
    val hash: String
)