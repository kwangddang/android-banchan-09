package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.FoodItem

data class FoodDto(
    @SerializedName("body")
    val body: List<FoodItemDto>,
    @SerializedName("statusCode")
    val statusCode: Int
) {
    fun toFoodList(): List<FoodItem> = body.map { it.toFoodItem() }
}