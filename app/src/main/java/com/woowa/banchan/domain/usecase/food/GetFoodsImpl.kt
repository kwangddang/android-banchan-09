package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.`interface`.GetFoods
import javax.inject.Inject

class GetFoodsImpl @Inject constructor(
    private val foodRepository: FoodRepository
): GetFoods {
    override suspend operator fun invoke(type: String): Result<Food> =
        runCatching {
            foodRepository.getFoods(type)
        }
}