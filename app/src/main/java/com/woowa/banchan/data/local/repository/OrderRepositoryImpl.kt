package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.order.OrderDataSource
import com.woowa.banchan.data.local.entity.*
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.freeShipping
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.shipping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {

    override suspend fun getTotalOrderList(): Result<Flow<List<Order>>> =
        runCatching {
            val flow = orderDataSource.getTotalOrderList().getOrThrow()
            flow.map { list -> list.map { it.toOrder() } }
        }

    override suspend fun getEachOrder(orderId: Long): Result<Order> =
        runCatching {
            val item = orderDataSource.getOrder(orderId).getOrThrow()
            item.toOrder()
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

            val orderDto = orderDataSource.insertNewOrderAndItem(
                newOrderDto(cart.size, totPrice, cart.first()),
                cart.map { it.toCartDto().toOrderItemDto(-1) }
            ).getOrThrow()
            orderDto.toOrder()
        }

    override suspend fun updateOrder(id: Long, deliverState: Boolean): Result<Unit> =
        runCatching { orderDataSource.updateOrder(id, deliverState) }

    override suspend fun getOrderState(): Result<Flow<Boolean>> =
        runCatching { orderDataSource.getOrderState().getOrThrow() }

}