package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
): InsertCartUseCase {
    override suspend fun insertCart(foodItem: FoodItem, totalCount: Int): Flow<UiState<Unit>> =
        flow {
            emit(UiState.Loading)
            cartRepository.insertCart(foodItem.toCart(totalCount,true))
                .onSuccess { emit(UiState.Success(it)) }
                .onFailure { emit(UiState.Error(it.message)) }
        }.flowOn(Dispatchers.IO)

}