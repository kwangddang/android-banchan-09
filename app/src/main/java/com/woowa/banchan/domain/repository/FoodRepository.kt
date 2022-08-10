package com.woowa.banchan.domain.repository

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodRepository {

    suspend fun getBestFoods() : Response<BestFood>

    suspend fun getFoods(type: String) : Response<Food>
}