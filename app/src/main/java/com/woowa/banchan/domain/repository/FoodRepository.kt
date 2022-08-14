package com.woowa.banchan.domain.repository

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.domain.model.DetailItem

interface FoodRepository {

    suspend fun getBestFoods(): Result<BestFood>

    suspend fun getFoods(type: String): Result<Food>

    suspend fun getDetailFood(hash: String): Result<DetailItem>
}