package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetDetailFoodUseCase {
    suspend operator fun invoke(hash: String): Flow<UiState<DetailItem>>
}