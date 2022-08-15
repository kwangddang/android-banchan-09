package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.model.DetailItem

interface FoodRepository {

    suspend fun getBestFoods(): Result<List<BestFoodCategory>>

    suspend fun getFoods(type: String): Result<List<FoodItem>>

    suspend fun getDetailFood(hash: String): Result<DetailItem>
}