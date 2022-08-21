package com.woowa.banchan.data.local.datasource.order

import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {

    suspend fun getTotalOrderList(): Result<Flow<List<OrderDto>>>
    suspend fun getOrderDetail(orderId: Long): Result<List<OrderItemDto>>

    suspend fun getOrder(orderId: Long): Result<OrderDto>

    suspend fun insertNewOrder(orderDto: OrderDto): Result<Long>
    suspend fun insertNewOrderItem(orderItemDto: List<OrderItemDto>): Result<Unit>

    suspend fun insertNewOrderAndItem(
        newOrder: OrderDto,
        orderItemList: List<OrderItemDto>
    ): Result<OrderDto>
}