package com.woowa.banchan.domain.repository

import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.data.remote.dto.FoodDto

interface FoodRepository {

    suspend fun getBestFoods(): Result<BestFoodDto>

    suspend fun getFoods(type: String): Result<FoodDto>
}