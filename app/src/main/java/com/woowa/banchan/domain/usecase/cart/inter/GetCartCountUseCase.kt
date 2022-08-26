package com.woowa.banchan.domain.usecase.cart.inter

import kotlinx.coroutines.flow.Flow

interface GetCartCountUseCase {
    suspend operator fun invoke(): Result<Flow<Int>>
}