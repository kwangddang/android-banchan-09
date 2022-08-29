package com.woowa.banchan.domain.usecase.recent

import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.repository.RecentRepository
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentlyViewedFoodsUseCaseImpl @Inject constructor(
    private val recentRepository: RecentRepository
) : GetRecentlyViewedFoodsUseCase {

    override suspend operator fun invoke(): Result<Flow<List<Recent>>> =
        runCatching { recentRepository.getRecentList() }
}