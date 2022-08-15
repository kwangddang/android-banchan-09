package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.order.OrderDataSource
import com.woowa.banchan.data.local.entity.toOrder
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {

    override suspend fun getTotalOrderList(): Result<List<Order>> =
        runCatching {
            val list = orderDataSource.getTotalOrderList().getOrThrow()
            list.map { it.toOrder() }
        }
}