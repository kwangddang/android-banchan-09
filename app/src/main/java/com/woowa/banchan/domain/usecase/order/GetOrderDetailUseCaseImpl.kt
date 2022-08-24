package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetOrderDetailUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderDetailUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetOrderDetailUseCase {

    override suspend operator fun invoke(orderId: Long): Result<Flow<List<OrderItem>>> =
        runCatching { orderRepository.getOrderDetail(orderId) }
}