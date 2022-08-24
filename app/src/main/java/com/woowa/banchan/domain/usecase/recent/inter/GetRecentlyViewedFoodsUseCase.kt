package com.woowa.banchan.domain.usecase.recent.inter

import com.woowa.banchan.domain.model.Recent
import kotlinx.coroutines.flow.Flow

interface GetRecentlyViewedFoodsUseCase {

    suspend operator fun invoke(): Result<Flow<List<Recent>>>
}