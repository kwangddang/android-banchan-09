package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetFoodsUseCase {

    override suspend operator fun invoke(type: String): Flow<UiState<Food>> =
        flow {
            emit(UiState.Loading)
            foodRepository.getFoods(type).onSuccess {
                emit(UiState.Success(it))
            }.onFailure {
                emit(UiState.Error(it.message))
            }
        }
}