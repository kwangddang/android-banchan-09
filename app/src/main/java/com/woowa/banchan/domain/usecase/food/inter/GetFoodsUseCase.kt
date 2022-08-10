package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.data.remote.dto.Food

interface GetFoodsUseCase {
    suspend operator fun invoke(type: String): Result<Food>
}