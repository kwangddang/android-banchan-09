package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.FoodDetail

data class FoodDetailDto(
    @SerializedName("data")
    val data: DetailItemDto,
    @SerializedName("hash")
    val hash: String
) {
    fun toFoodDetail(): FoodDetail =
        FoodDetail(
            data = data,
            hash = hash
        )
}