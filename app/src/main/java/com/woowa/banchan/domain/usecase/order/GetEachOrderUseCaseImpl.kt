package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetEachOrderUseCase
import javax.inject.Inject

class GetEachOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetEachOrderUseCase {

    override suspend operator fun invoke(orderId: Long): Result<Order> =
        runCatching { orderRepository.getEachOrder(orderId) }
}