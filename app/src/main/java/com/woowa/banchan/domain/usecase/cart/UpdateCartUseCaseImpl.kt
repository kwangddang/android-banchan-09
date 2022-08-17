package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.UpdateCartUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : UpdateCartUseCase {

    override suspend operator fun invoke(cart: Cart): Flow<UiState<Unit>> = flow {
        emit(UiState.Loading)
        cartRepository.updateCart(cart)
            .onSuccess { emit(UiState.Success(Unit)) }
            .onFailure { emit(UiState.Error(it.message)) }
    }.flowOn(Dispatchers.IO)
}