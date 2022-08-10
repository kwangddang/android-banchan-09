package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import javax.inject.Inject

class GetFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetFoodsUseCase {
    override suspend operator fun invoke(type: String): Result<Food> =
        runCatching {
            foodRepository.getFoods(type)
        }
}