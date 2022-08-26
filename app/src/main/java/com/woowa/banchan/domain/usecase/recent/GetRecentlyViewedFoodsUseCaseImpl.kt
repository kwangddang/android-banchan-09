package com.woowa.banchan.domain.usecase.recent

import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.repository.RecentRepository
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentlyViewedFoodsUseCaseImpl @Inject constructor(
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository
) : GetRecentlyViewedFoodsUseCase {

    override suspend operator fun invoke(): Result<Flow<List<Recent>>> = runCatching {
        val cartFlow = cartRepository.getCartList()
        val recentFood = recentRepository.getRecentList()

        cartFlow.map { map ->
            recentFood.map { recent ->
                recent.copy(
                    checkState = map.containsKey(recent.hash)
                )
            }
        }
    }
}