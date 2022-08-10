package com.woowa.banchan.data.remote.datasource.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.data.remote.service.BanchanService
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor(
    private val banchanService: BanchanService
) : FoodDataSource {
    override suspend fun getBestFoods(): BestFood =
        banchanService.getBestFoods()

    override suspend fun getFoods(type: String): Food =
        banchanService.getFoods(type)
}