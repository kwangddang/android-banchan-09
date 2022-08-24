package com.woowa.banchan.domain.usecase.recent

import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.repository.RecentRepository
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentlyViewedFoodsUseCaseImpl @Inject constructor(
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository
) : GetRecentlyViewedFoodsUseCase {

    override suspend operator fun invoke(): Flow<UiState<List<Recent>>> {
        var message: String? = null

        runCatching {
            val cartFlow = cartRepository.getCartList()
            val recentFood = recentRepository.getRecentList().getOrThrow()

            return cartFlow.map { map ->
                UiState.Success(
                    recentFood.map { recent ->
                        recent.copy(
                            checkState = map.containsKey(recent.hash)
                        )
                    }
                )
            }
        }.onFailure {
            message = it.message
        }
        return flow { UiState.Error(message) }
    }
}