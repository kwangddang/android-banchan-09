package com.woowa.banchan.data.repository.order

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import kotlinx.coroutines.flow.Flow

interface OrderRepositoryTest {

    suspend fun getTotalOrderList(): Result<Flow<List<Order>>>
    suspend fun getEachOrder(orderId: Long): Result<Order>
    suspend fun getOrderDetail(orderId: Long): Result<List<OrderItem>>
    suspend fun insertCartToOrder(cart: List<Cart>): Result<Order>
    suspend fun updateOrder(id: Long, deliverState: Boolean): Result<Unit>
    suspend fun getOrderState(): Result<Flow<Boolean>>
}