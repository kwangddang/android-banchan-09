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

    override suspend fun getBestFoods(): List<BestFoodCategory> =
        foodDataSource.getBestFoods().toBestFoodCategoryList()

    override suspend fun getFoods(type: String): List<FoodItem> =
        foodDataSource.getFoods(type).toFoodList()

    override suspend fun getDetailFood(hash: String): DetailItem =
        foodDataSource.getDetailFood(hash).toFoodDetail()
}