package com.woowa.banchan.domain.usecase.order.inter

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order

interface InsertCartToOrderUseCase {

    suspend operator fun invoke(cartList: List<Cart>): Result<Long>
}