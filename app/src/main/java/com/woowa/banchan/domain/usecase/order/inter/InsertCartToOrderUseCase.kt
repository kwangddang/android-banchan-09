package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface InsertCartToOrderUseCase {

    suspend operator fun invoke(cartList: List<Cart>): Flow<UiState<Order>>
}