package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.model.FoodItem

interface FoodRepository {

    suspend fun getBestFoods(): List<BestFoodCategory>

    suspend fun getFoods(type: String): List<FoodItem>

    suspend fun getDetailFood(hash: String): DetailItem
}