package com.woowa.banchan.domain.usecase.recent

import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.repository.RecentRepository
import com.woowa.banchan.domain.usecase.recent.inter.InsertRecentlyViewedFoodsUseCase
import javax.inject.Inject

class InsertRecentlyViewedFoodsUseCaseImpl @Inject constructor(
    private val recentRepository: RecentRepository
) : InsertRecentlyViewedFoodsUseCase {

    override suspend fun invoke(
        detailItem: DetailItem,
        title: String,
        totalCount: Int
    ): Result<Unit> =
        runCatching { recentRepository.insertRecent(detailItem.toRecent(title)) }
}