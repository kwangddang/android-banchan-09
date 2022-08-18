package com.woowa.banchan.domain.usecase.recent

import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.repository.RecentRepository
import com.woowa.banchan.domain.usecase.recent.inter.InsertRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertRecentlyViewedFoodsUseCaseImpl @Inject constructor(
    private val recentRepository: RecentRepository
) : InsertRecentlyViewedFoodsUseCase {

    override suspend fun invoke(
        detailItem: DetailItem,
        title: String,
        totalCount: Int
    ): Flow<UiState<Unit>> =
        flow {
            emit(UiState.Loading)
            recentRepository.insertRecent(detailItem.toRecent(title))
                .onSuccess { emit(UiState.Success(it)) }
                .onFailure { emit(UiState.Error(it.message)) }
        }.flowOn(Dispatchers.IO)
}