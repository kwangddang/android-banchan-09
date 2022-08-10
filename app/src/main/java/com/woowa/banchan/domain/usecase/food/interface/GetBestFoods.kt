package com.woowa.banchan.domain.usecase.food.`interface`

import com.woowa.banchan.data.remote.dto.BestFood

interface GetBestFoods {
    suspend operator fun invoke(): Result<BestFood>
}