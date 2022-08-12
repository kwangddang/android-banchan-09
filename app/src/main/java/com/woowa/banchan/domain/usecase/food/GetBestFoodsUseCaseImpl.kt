package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBestFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetBestFoodsUseCase {

    override suspend operator fun invoke(): Flow<UiState<BestFood>> =
        withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(UiState.Loading)
                foodRepository.getBestFoods().onSuccess {
                    emit(UiState.Success(it))
                }.onFailure {
                    emit(UiState.Error(it.message))
                }
            }
        }
}