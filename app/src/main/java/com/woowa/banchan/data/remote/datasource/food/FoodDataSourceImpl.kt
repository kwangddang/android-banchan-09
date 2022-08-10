package com.woowa.banchan.data.remote.datasource.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import retrofit2.Response
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor() : FoodDataSource {
    override suspend fun getBestFoods(): Response<BestFood> {
        TODO("Not yet implemented")
    }

    override suspend fun getFoods(type: String): Response<Food> {
        TODO("Not yet implemented")
    }
}