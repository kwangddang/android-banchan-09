package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.`interface`.GetBestFoods
import javax.inject.Inject

class GetBestFoodsImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetBestFoods {

    override suspend operator fun invoke(): Result<BestFood> =
        runCatching {
            foodRepository.getBestFoods()
        }
}