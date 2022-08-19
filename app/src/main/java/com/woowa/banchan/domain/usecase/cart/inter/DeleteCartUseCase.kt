package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface DeleteCartUseCase {

    suspend operator fun invoke(hash: String): Flow<UiState<Unit>>
}