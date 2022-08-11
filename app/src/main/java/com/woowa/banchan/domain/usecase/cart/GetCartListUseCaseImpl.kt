package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCartListUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : GetCartListUseCase {

    override suspend operator fun invoke(): Flow<UiState<List<Cart>>> =
        withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(UiState.Loading)
                cartRepository.getCartList()
                    .onSuccess { emit(UiState.Success(it)) }
                    .onFailure { emit(UiState.Error(it.message)) }
            }
        }
}