package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.GetCartCountUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartCountUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : GetCartCountUseCase {
    override suspend fun invoke(): Flow<UiState<Int>> {
        var message: String? = null

        runCatching {
            return cartRepository.getCartCount().getOrThrow().map { UiState.Success(it) }
        }.onFailure {
            message = it.message
        }
        return flow { UiState.Error(message) }
    }
}