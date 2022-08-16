package com.woowa.banchan.data.local.datasource.order

import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto

interface OrderDataSource {

    suspend fun getTotalOrderList(): Result<List<OrderDto>>
    suspend fun getOrderDetail(orderId: Long): Result<List<OrderItemDto>>

    suspend fun getOrder(orderId: Long): Result<OrderDto>

    suspend fun insertNewOrder(orderDto: OrderDto): Result<Long>
    suspend fun insertNewOrderItem(orderItemDto: List<OrderItemDto>): Result<Unit>
}