package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.OrderItem

interface GetOrderDetailUseCase {

    suspend operator fun invoke(orderId: Long): Result<List<OrderItem>>
}