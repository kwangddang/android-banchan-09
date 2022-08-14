package com.woowa.banchan.data.remote.repository

import com.woowa.banchan.data.remote.datasource.food.FoodDataSource
import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodDataSource: FoodDataSource
) : FoodRepository {

    override suspend fun getBestFoods(): Result<BestFood> =
        runCatching {
            foodDataSource.getBestFoods()
        }

    override suspend fun getFoods(type: String): Result<Food> =
        runCatching {
            foodDataSource.getFoods(type)
        }

    override suspend fun getDetailFood(hash: String): Result<DetailItem> =
        runCatching {
            foodDataSource.getDetailFood(hash).toFoodDetail()
        }
}