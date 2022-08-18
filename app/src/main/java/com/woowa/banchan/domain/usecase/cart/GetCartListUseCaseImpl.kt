package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartListUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : GetCartListUseCase {

    override suspend operator fun invoke(): Flow<UiState<Map<String, Cart>>> {
        var message: String? = null

        runCatching {
            return cartRepository.getCartList().getOrThrow().map { UiState.Success(it) }
        }.onFailure {
            message = it.message
        }
        return flow { UiState.Error(message) }
    }
}