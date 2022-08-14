package com.woowa.banchan.domain.usecase.food

import android.util.Log
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetDetailFoodUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailFoodUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetDetailFoodUseCase {
    override suspend fun invoke(hash: String): Flow<UiState<DetailItem>> =
        flow {
            emit(UiState.Loading)
            foodRepository.getDetailFood(hash)
                .onSuccess { emit(UiState.Success(it)) }
                .onFailure { emit(UiState.Error(it.message)) }
        }
}