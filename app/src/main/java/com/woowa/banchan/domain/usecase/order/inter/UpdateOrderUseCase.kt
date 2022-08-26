package com.woowa.banchan.domain.usecase.order.inter

interface UpdateOrderUseCase {

    suspend operator fun invoke(id: Long, deliverState: Boolean): Result<Unit>
}