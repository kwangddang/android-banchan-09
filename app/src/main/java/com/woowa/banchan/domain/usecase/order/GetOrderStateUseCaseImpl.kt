package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetOrderStateUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOrderStateUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetOrderStateUseCase {
    override suspend fun invoke(): Flow<UiState<Boolean>> {
        var message: String? = null

        runCatching {
            return orderRepository.getOrderState().getOrThrow().map { UiState.Success(it) }
        }.onFailure {
            message = it.message
        }
        return flow { UiState.Error(message) }
    }

}