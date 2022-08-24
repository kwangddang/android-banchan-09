package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.InsertCartToOrderUseCase
import javax.inject.Inject

class InsertCartToOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : InsertCartToOrderUseCase {

    override suspend operator fun invoke(cartList: List<Cart>): Result<Long> =
        runCatching { orderRepository.insertCartToOrder(cartList) }
}