package com.woowa.banchan.data.remote.datasource.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.data.remote.dto.FoodDetailDto

interface FoodDataSource {

    suspend fun getBestFoods(): BestFood

    suspend fun getFoods(type: String): Food

    suspend fun getDetailFood(hash: String): FoodDetailDto
}