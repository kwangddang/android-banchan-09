package com.woowa.banchan.domain.usecase.food.`interface`

import com.woowa.banchan.data.remote.dto.Food

interface GetFoods {
    suspend operator fun invoke(type: String): Result<Food>
}