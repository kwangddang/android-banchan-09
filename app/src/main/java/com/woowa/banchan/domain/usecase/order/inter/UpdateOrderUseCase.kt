package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.ui.common.uistate.UiState

interface UpdateOrderUseCase {

    suspend operator fun invoke(id: Long, deliverState: Boolean): UiState<Unit>
}