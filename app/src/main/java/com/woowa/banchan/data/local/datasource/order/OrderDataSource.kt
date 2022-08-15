package com.woowa.banchan.data.local.datasource.order

import com.woowa.banchan.data.local.entity.OrderDto

interface OrderDataSource {

    suspend fun getTotalOrderList(): Result<List<OrderDto>>
}