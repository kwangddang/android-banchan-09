package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetCartListUseCase {

    suspend operator fun invoke(): Flow<UiState<List<Cart>>>
}