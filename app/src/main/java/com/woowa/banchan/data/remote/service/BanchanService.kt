package com.woowa.banchan.data.remote.service

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import retrofit2.http.GET
import retrofit2.http.Path

interface BanchanService {
    @GET("best")
    suspend fun getBestFoods() : BestFood

    @GET("{type}")
    suspend fun getFoods(@Path("type") type: String) : Food
}
