package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.BestFoodCategory

data class BestFoodCategoryDto(
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("items")
    val items: List<FoodItemDto>,
    @SerializedName("name")
    val name: String
){
    fun toBestFoodCategory() =
        BestFoodCategory(
            categoryId = categoryId,
            items = items.map { it.toFoodItem() },
            name = name
        )
}