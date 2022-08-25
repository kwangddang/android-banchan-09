package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.order.OrderDataSource
import com.woowa.banchan.data.local.entity.newOrderDto
import com.woowa.banchan.data.local.entity.toCartDto
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
        withContext(Dispatchers.IO) {
            orderDataSource.getTotalOrderList().map { list -> list.map { it.toOrder() } }
        }

    override suspend fun getEachOrder(orderId: Long): Flow<Order> =
        withContext(Dispatchers.IO) {
            orderDataSource.getOrder(orderId).map { it.toOrder() }
        }

    override suspend fun getOrderDetail(orderId: Long): Flow<List<OrderItem>> =
        withContext(Dispatchers.IO) {
            orderDataSource.getOrderDetail(orderId).map { list -> list.map { it.toOrderItem() } }
        }

    override suspend fun insertCartToOrder(cart: List<Cart>): Long =
        withContext(Dispatchers.IO) {
            var totPrice = 0
            cart.forEach { totPrice += (it.price * it.count) }
            totPrice += (if (totPrice >= freeShipping) 0 else shipping)

            orderDataSource.insertNewOrderAndItem(
                newOrderDto(cart.size, totPrice, cart.first()),
                cart.map { it.toCartDto().toOrderItemDto(-1) }
            )
        }

    override suspend fun updateOrder(id: Long, deliverState: Boolean) =
        withContext(Dispatchers.IO) {
            orderDataSource.updateOrder(id, deliverState)
        }

    override suspend fun getOrderState(): Flow<Boolean> =
        withContext(Dispatchers.IO) {
            orderDataSource.getOrderState()
        }
}