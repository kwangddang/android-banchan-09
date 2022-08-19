package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : DeleteCartUseCase {

    override suspend operator fun invoke(hash: String): Flow<UiState<Unit>> = flow {
        emit(UiState.Loading)
        cartRepository.deleteCart(hash)
            .onSuccess { emit(UiState.Success(Unit)) }
            .onFailure { emit(UiState.Error(it.message)) }
    }.flowOn(Dispatchers.IO)
}