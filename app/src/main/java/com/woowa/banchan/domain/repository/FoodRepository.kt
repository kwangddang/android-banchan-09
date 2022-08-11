package com.woowa.banchan.domain.repository

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food

interface FoodRepository {

    suspend fun getBestFoods(): Result<BestFood>

    suspend fun getFoods(type: String): Result<Food>
}