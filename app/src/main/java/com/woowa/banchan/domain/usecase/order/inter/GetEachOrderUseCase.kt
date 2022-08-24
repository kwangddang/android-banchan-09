package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.Order

interface GetEachOrderUseCase {

    suspend operator fun invoke(orderId: Long): Result<Order>
}