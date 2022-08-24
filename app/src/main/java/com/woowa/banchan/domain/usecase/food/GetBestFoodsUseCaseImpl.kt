package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBestFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository,
    private val cartRepository: CartRepository
) : GetBestFoodsUseCase {

    override suspend operator fun invoke(): Result<Flow<List<BestFoodCategory>>> = runCatching {
        val cartFlow = cartRepository.getCartList()
        val foodCategory = foodRepository.getBestFoods()

        cartFlow.map { map ->
            foodCategory.map { foodCategory ->
                foodCategory.copy(
                    items = foodCategory.items.map { foodItem ->
                        foodItem.copy(checkState = map.containsKey(foodItem.detailHash))
                    }
                )
            }
        }
    }
}