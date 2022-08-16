package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.order.OrderDataSource
import com.woowa.banchan.data.local.entity.newOrderDto
import com.woowa.banchan.data.local.entity.toOrder
import com.woowa.banchan.data.local.entity.toOrderItem
import com.woowa.banchan.data.local.entity.toOrderItemDto
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.domain.model.toCartDto
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.freeShipping
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.shipping
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {

    override suspend fun getTotalOrderList(): Result<List<Order>> =
        runCatching {
            val list = orderDataSource.getTotalOrderList().getOrThrow()
            list.map { it.toOrder() }
        }

    override suspend fun getOrderDetail(orderId: Long): Result<List<OrderItem>> =
        runCatching {
            val list = orderDataSource.getOrderDetail(orderId).getOrThrow()
            list.map { it.toOrderItem() }
        }

    override suspend fun insertCartToOrder(cart: List<Cart>): Result<Order> =
        runCatching {
            var totPrice = 0
            cart.forEach { totPrice += (it.price * it.count) }
            totPrice += (if (totPrice >= freeShipping) 0 else shipping)

            val orderId =
                orderDataSource.insertNewOrder(newOrderDto(cart.size, totPrice, cart.first()))
                    .getOrThrow()
            orderDataSource.insertNewOrderItem(cart.map { it.toCartDto().toOrderItemDto(orderId) })

            val orderDto = orderDataSource.getOrder(orderId = orderId).getOrThrow()
            orderDto.toOrder()
        }
}