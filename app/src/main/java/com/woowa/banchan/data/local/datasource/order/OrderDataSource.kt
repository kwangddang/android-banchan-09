package com.woowa.banchan.data.local.datasource.order

import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto

interface OrderDataSource {

    suspend fun getTotalOrderList(): Result<List<OrderDto>>
    suspend fun getOrderDetail(orderId: Long): Result<List<OrderItemDto>>
}