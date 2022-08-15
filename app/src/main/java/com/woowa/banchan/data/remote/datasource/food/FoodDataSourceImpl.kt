package com.woowa.banchan.data.remote.datasource.food

import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.data.remote.dto.FoodDto
import com.woowa.banchan.data.remote.dto.FoodDetailDto
import com.woowa.banchan.data.remote.service.BanchanService
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor(
    private val banchanService: BanchanService
) : FoodDataSource {

    override suspend fun getBestFoods(): BestFoodDto =
        banchanService.getBestFoods()

    override suspend fun getFoods(type: String): FoodDto =
        banchanService.getFoods(type)

    override suspend fun getDetailFood(hash: String): FoodDetailDto =
        banchanService.getDetailFood(hash)

}