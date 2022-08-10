package com.woowa.banchan.data.remote.repository

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.domain.repository.FoodRepository
import retrofit2.Response
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor() : FoodRepository {
    override suspend fun getBestFoods(): Response<BestFood> {
        TODO("Not yet implemented")
    }

    override suspend fun getFoods(type: String): Response<Food> {
        TODO("Not yet implemented")
    }
}