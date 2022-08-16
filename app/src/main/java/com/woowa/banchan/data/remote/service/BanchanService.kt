package com.woowa.banchan.data.remote.service

import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.data.remote.dto.FoodDetailDto
import com.woowa.banchan.data.remote.dto.FoodDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BanchanService {
    @GET("best")
    suspend fun getBestFoods(): BestFoodDto

    @GET("{type}")
    suspend fun getFoods(@Path("type") type: String): FoodDto

    @GET("detail/{hash}")
    suspend fun getDetailFood(@Path("hash") hash: String): FoodDetailDto
}
