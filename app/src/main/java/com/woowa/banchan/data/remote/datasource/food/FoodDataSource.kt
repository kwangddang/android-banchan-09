package com.woowa.banchan.data.remote.datasource.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.data.remote.dto.Food
import retrofit2.Response

interface FoodDataSource {

    suspend fun getBestFoods() : Response<BestFood>

    suspend fun getFoods(type: String) : Response<Food>
}