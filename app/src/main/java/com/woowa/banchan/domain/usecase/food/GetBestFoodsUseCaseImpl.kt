package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBestFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository,
    private val cartRepository: CartRepository
) : GetBestFoodsUseCase {

    override suspend operator fun invoke(): Flow<UiState<List<BestFoodCategory>>> {
        var message: String? = null

        runCatching {
            val cartFlow = cartRepository.getCartList().getOrThrow()
            val foodCategory = foodRepository.getBestFoods().getOrThrow()

            return cartFlow.map { map ->
                UiState.Success(
                    foodCategory.map { foodCategory ->
                        foodCategory.copy(
                            items = foodCategory.items.map { foodItem ->
                                foodItem.copy(checkState = map.containsKey(foodItem.detailHash))
                            }
                        )
                    }
                )
            }
        }.onFailure {
            message = it.message
        }
        return flow { UiState.Error(message) }
    }
}