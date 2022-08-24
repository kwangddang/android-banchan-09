package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface GetEachOrderUseCase {

    suspend operator fun invoke(orderId: Long): Result<Flow<Order>>
}