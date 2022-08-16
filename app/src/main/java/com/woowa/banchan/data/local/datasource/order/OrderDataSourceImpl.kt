package com.woowa.banchan.data.local.datasource.order

import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.dao.OrderItemDao
import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao
) : OrderDataSource {

    override suspend fun getTotalOrderList(): Result<List<OrderDto>> =
        runCatching { orderDao.getTotalOrderList() }

    override suspend fun getOrderDetail(orderId: Long): Result<List<OrderItemDto>> =
        runCatching { orderItemDao.getOrderDetail(orderId) }

}