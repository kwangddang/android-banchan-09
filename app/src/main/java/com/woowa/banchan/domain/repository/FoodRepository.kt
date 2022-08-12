package com.woowa.banchan.domain.repository

import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.data.remote.dto.FoodDto
import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.model.FoodItem

interface FoodRepository {

    suspend fun getBestFoods(): Result<List<BestFoodCategory>>

    suspend fun getFoods(type: String): Result<List<FoodItem>>
}