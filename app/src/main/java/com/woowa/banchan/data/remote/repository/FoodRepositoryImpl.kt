package com.woowa.banchan.data.remote.repository

import com.woowa.banchan.data.remote.datasource.food.FoodDataSource
import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.data.remote.dto.FoodDto
import com.woowa.banchan.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodDataSource: FoodDataSource
) : FoodRepository {

    override suspend fun getBestFoods(): Result<BestFoodDto> =
        runCatching {
            foodDataSource.getBestFoods()
        }

    override suspend fun getFoods(type: String): Result<FoodDto> =
        runCatching {
            foodDataSource.getFoods(type)
        }
}