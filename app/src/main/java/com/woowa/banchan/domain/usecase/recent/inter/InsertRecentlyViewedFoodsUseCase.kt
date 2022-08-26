package com.woowa.banchan.domain.usecase.recent.inter

import com.woowa.banchan.domain.model.DetailItem

interface InsertRecentlyViewedFoodsUseCase {

    suspend operator fun invoke(
        detailItem: DetailItem,
        title: String,
        totalCount: Int
    ): Result<Unit>
}