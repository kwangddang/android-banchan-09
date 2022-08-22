package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetCartCountUseCase {
    suspend operator fun invoke(): Flow<UiState<Int>>
}