package com.woowa.banchan.domain.usecase.recent.inter

import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface InsertRecentlyViewedFoodsUseCase {

    suspend operator fun invoke(detailItem: DetailItem, title: String, totalCount: Int): Flow<UiState<Unit>>
}