package com.woowa.banchan.domain.usecase.order.inter

import kotlinx.coroutines.flow.Flow

interface GetOrderStateUseCase {

    suspend operator fun invoke(): Result<Flow<Boolean>>
}