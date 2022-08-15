package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBestFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetBestFoodsUseCase {

    override suspend operator fun invoke(): Flow<UiState<List<BestFoodCategory>>> =
        flow {
            emit(UiState.Loading)
            foodRepository.getBestFoods().onSuccess {
                emit(UiState.Success(it))
            }.onFailure {
                emit(UiState.Error(it.message))
            }
        }
}