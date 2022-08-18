package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetEachOrderUseCase {

    suspend operator fun invoke(orderId: Long): Flow<UiState<Order>>
}