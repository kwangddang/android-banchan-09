package com.woowa.banchan.domain.usecase.food

import com.woowa.banchan.domain.UiState
import com.woowa.banchan.domain.repository.FoodRepository
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFoodsUseCaseImpl @Inject constructor(
    private val foodRepository: FoodRepository
) : GetFoodsUseCase {

    override suspend operator fun invoke(type: String): Flow<UiState> =
        withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(UiState.Loading)
                foodRepository.getFoods(type).onSuccess {
                    emit(UiState.Success(it))
                }.onFailure {
                    emit(UiState.Error(it.message))
                }
            }
        }
}