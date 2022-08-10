package com.woowa.banchan.data.remote.datasource.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.data.remote.service.BanchanService
import retrofit2.Response
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor(
    private val banchanService: BanchanService
) : FoodDataSource {
    override suspend fun getBestFoods(): Response<BestFood> =
        banchanService.getBestFoods()

    override suspend fun getFoods(type: String): Response<Food> =
        banchanService.getFoods(type)
}