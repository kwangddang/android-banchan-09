package com.woowa.banchan.domain.usecase.recent.inter

import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetRecentlyViewedFoodsUseCase {

    suspend operator fun invoke(): Flow<UiState<List<Recent>>>
}