package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.data.remote.dto.BestFood

interface GetBestFoodsUseCase {
    suspend operator fun invoke(): Result<BestFood>
}