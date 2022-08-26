package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetDetailFoodUseCase
import javax.inject.Inject

class GetDetailFoodUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetDetailFoodUseCase {

    override suspend fun invoke(hash: String): Result<DetailItem> =
        runCatching { foodRepository.getDetailFood(hash) }
}