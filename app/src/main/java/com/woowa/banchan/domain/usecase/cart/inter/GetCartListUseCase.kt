package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface GetCartListUseCase {

    suspend operator fun invoke(): Result<Flow<Map<String, Cart>>>
}