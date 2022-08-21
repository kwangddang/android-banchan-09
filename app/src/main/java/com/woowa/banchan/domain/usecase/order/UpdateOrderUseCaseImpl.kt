package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.UpdateOrderUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import javax.inject.Inject

class UpdateOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : UpdateOrderUseCase {

    override suspend operator fun invoke(id:Long, deliverState: Boolean): UiState<Unit> {
        orderRepository.updateOrder(id, deliverState).onSuccess {
            return UiState.Success(Unit)
        }.onFailure {
            return UiState.Error(it.message)
        }
        return UiState.Error("")
    }
}