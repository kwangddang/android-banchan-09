package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.UpdateOrderUseCase
import javax.inject.Inject

class UpdateOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : UpdateOrderUseCase {

    override suspend operator fun invoke(id: Long, deliverState: Boolean): Result<Unit> =
        runCatching { orderRepository.updateOrder(id, deliverState) }
}