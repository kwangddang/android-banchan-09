package com.woowa.banchan.data.repository.food

import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.model.FoodItem

interface FoodRepositoryTest {

    suspend fun getBestFoods(): Result<List<BestFoodCategory>>

    suspend fun getFoods(type: String): Result<List<FoodItem>>

    suspend fun getDetailFood(hash: String): Result<DetailItem>
}