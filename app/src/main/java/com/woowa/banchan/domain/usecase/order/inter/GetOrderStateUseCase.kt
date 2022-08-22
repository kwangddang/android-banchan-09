package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetOrderStateUseCase {
    suspend operator fun invoke(): Flow<UiState<Boolean>>
}