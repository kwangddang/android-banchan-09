package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun getTotalOrderList(): Flow<List<Order>>

    suspend fun getEachOrder(orderId: Long): Flow<Order>

    suspend fun getOrderDetail(orderId: Long): Flow<List<OrderItem>>

    suspend fun insertCartToOrder(cart: List<Cart>): Long

    suspend fun updateOrder(id: Long, deliverState: Boolean)

    suspend fun getOrderState(): Flow<Boolean>
}