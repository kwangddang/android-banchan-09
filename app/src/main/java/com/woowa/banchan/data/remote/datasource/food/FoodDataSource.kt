package com.woowa.banchan.data.remote.datasource.food

import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.data.remote.dto.FoodDetailDto
import com.woowa.banchan.data.remote.dto.FoodDto

interface FoodDataSource {

    suspend fun getBestFoods(): BestFoodDto

    suspend fun getFoods(type: String): FoodDto

    suspend fun getDetailFood(hash: String): FoodDetailDto
}