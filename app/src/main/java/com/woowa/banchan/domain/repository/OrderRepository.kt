package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem

interface OrderRepository {

    suspend fun getTotalOrderList(): Result<List<Order>>
    suspend fun getOrderDetail(orderId: Long): Result<List<OrderItem>>
}