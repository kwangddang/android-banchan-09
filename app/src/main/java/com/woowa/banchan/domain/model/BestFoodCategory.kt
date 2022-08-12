package com.woowa.banchan.domain.model

data class BestFoodCategory(
    val categoryId: String,
    val items: List<FoodItem>,
    val name: String
)
