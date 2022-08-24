package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.domain.model.DetailItem

interface GetDetailFoodUseCase {
    suspend operator fun invoke(hash: String): Result<DetailItem>
}