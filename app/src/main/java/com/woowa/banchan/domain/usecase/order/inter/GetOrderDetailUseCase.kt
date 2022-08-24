package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.OrderItem
import kotlinx.coroutines.flow.Flow

interface GetOrderDetailUseCase {

    suspend operator fun invoke(orderId: Long): Result<Flow<List<OrderItem>>>
}