package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.BestFoodCategory

data class BestFoodDto(
    @SerializedName("body")
    val body: List<BestFoodCategoryDto>,
    @SerializedName("statusCode")
    val statusCode: Int
) {
    fun toBestFoodCategoryList(): List<BestFoodCategory> = body.map { it.toBestFoodCategory() }
}