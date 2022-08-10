package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import javax.inject.Inject

class GetBestFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetBestFoodsUseCase {

    override suspend operator fun invoke(): Result<BestFood> =
        runCatching {
            foodRepository.getBestFoods()
        }
}