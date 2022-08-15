package com.woowa.banchan.data.local.datasource.order

import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.entity.OrderDto
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderDataSource {

    override suspend fun getTotalOrderList(): Result<List<OrderDto>> =
        runCatching { orderDao.getTotalOrderList() }
}