package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName

data class BestFoodCategory(
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("items")
    val items: List<FoodItem>,
    @SerializedName("name")
    val name: String
)