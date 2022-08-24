package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetTotalOrderUseCase {

    override suspend operator fun invoke(): Result<Flow<List<Order>>> =
        runCatching { orderRepository.getTotalOrderList() }
}