package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.domain.model.BestFoodCategory
import kotlinx.coroutines.flow.Flow

interface GetBestFoodsUseCase {

    suspend operator fun invoke(): Result<Flow<List<BestFoodCategory>>>
}