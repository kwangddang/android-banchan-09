package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface GetTotalOrderUseCase {

    suspend operator fun invoke(): Result<Flow<List<Order>>>
}