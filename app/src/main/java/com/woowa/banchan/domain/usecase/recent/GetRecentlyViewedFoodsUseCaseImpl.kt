package com.woowa.banchan.domain.usecase.recent

import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.repository.RecentRepository
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecentlyViewedFoodsUseCaseImpl @Inject constructor(
    private val recentRepository: RecentRepository
) : GetRecentlyViewedFoodsUseCase {

    override suspend operator fun invoke(): Flow<UiState<List<Recent>>> =
        flow {
            emit(UiState.Loading)
            recentRepository.getRecentList()
                .onSuccess { emit(UiState.Success(it)) }
                .onFailure { emit(UiState.Error(it.message)) }
        }.flowOn(Dispatchers.IO)
}