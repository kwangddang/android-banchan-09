package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetOrderDetailUseCase {

    suspend operator fun invoke(orderId: Long): Flow<UiState<List<OrderItem>>>
}