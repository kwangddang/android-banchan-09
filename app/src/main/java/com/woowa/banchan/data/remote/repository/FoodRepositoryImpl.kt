package com.woowa.banchan.data.remote.repository

import com.woowa.banchan.data.remote.datasource.food.FoodDataSource
import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodDataSource: FoodDataSource
) : FoodRepository {

    override suspend fun getBestFoods(): Result<List<BestFoodCategory>> =
        runCatching {
            foodDataSource.getBestFoods().toBestFoodCategoryList()
        }

    override suspend fun getFoods(type: String): Result<List<FoodItem>> =
        runCatching {
            foodDataSource.getFoods(type).toFoodList()
        }

    override suspend fun getDetailFood(hash: String): Result<DetailItem> =
        runCatching {
            foodDataSource.getDetailFood(hash).toFoodDetail()
        }
}