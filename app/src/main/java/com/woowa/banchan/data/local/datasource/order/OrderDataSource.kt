package com.woowa.banchan.data.local.datasource.order

import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {

    suspend fun getTotalOrderList(): Flow<List<OrderDto>>

    suspend fun getOrderDetail(orderId: Long): Flow<List<OrderItemDto>>

    suspend fun getOrder(orderId: Long): Flow<OrderDto>

    suspend fun insertNewOrder(orderDto: OrderDto): Long

    suspend fun insertNewOrderItem(orderItemDto: List<OrderItemDto>)

    suspend fun insertNewOrderAndItem(
        newOrder: OrderDto,
        orderItemList: List<OrderItemDto>
    ): Long

    suspend fun updateOrder(id: Long, deliverState: Boolean)

    suspend fun getOrderState(): Flow<Boolean>

}