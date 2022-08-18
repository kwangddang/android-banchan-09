package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartListUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : GetCartListUseCase {

    override suspend operator fun invoke(): Result<Flow<Map<String, Cart>>> =
        runCatching { cartRepository.getCartList()  }
}