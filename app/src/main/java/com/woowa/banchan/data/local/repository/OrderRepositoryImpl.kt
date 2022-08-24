package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.order.OrderDataSource
import com.woowa.banchan.data.local.entity.*
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.freeShipping
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.shipping
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {

    override suspend fun getTotalOrderList(): Flow<List<Order>> =
        orderDataSource.getTotalOrderList().map { list -> list.map { it.toOrder() } }

    override suspend fun getEachOrder(orderId: Long): Order =
        orderDataSource.getOrder(orderId).toOrder()

    override suspend fun getOrderDetail(orderId: Long): List<OrderItem> =
        withContext(Dispatchers.IO) {
            orderDataSource.getOrderDetail(orderId).map { it.toOrderItem() }
        }

    override suspend fun insertCartToOrder(cart: List<Cart>): Order {
        var totPrice = 0
        cart.forEach { totPrice += (it.price * it.count) }
        totPrice += (if (totPrice >= freeShipping) 0 else shipping)

        val orderDto = orderDataSource.insertNewOrderAndItem(
            newOrderDto(cart.size, totPrice, cart.first()),
            cart.map { it.toCartDto().toOrderItemDto(-1) }
        )
        return orderDto.toOrder()
    }

    override suspend fun updateOrder(id: Long, deliverState: Boolean) =
        orderDataSource.updateOrder(id, deliverState)

    override suspend fun getOrderState(): Flow<Boolean> =
        orderDataSource.getOrderState()

}