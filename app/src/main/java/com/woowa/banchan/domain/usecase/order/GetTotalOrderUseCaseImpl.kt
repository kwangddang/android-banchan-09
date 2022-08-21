package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTotalOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetTotalOrderUseCase {

    override suspend operator fun invoke(): Flow<UiState<List<Order>>> {
        var message: String? = null

        runCatching {
            return orderRepository.getTotalOrderList().getOrThrow().map { UiState.Success(it) }
        }.onFailure {
            message = it.message
        }
        return flow { UiState.Error(message) }
    }
}