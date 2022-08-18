package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository,
    private val cartRepository: CartRepository
) : GetFoodsUseCase {

    override suspend operator fun invoke(type: String): Result<Flow<List<FoodItem>>> =
        runCatching {
            val cartFlow = cartRepository.getCartList()
            val foodList = foodRepository.getFoods(type)

            cartFlow.map { map ->
                foodList.map { foodItem ->
                    foodItem.copy(checkState = map.containsKey(foodItem.detailHash))
                }
            }
        }
}
