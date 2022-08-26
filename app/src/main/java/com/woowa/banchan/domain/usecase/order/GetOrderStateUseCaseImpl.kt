package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetOrderStateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOrderStateUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetOrderStateUseCase {

    override suspend fun invoke(): Result<Flow<Boolean>> =
        runCatching { orderRepository.getOrderState().map { it } }
}