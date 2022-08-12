package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBestFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetBestFoodsUseCase {

    override suspend operator fun invoke(): Flow<UiState<BestFoodDto>> =
        flow {
            emit(UiState.Loading)
            foodRepository.getBestFoods().onSuccess {
                emit(UiState.Success(it))
            }.onFailure {
                emit(UiState.Error(it.message))
            }
        }
}