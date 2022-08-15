package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Order

interface OrderRepository {

    suspend fun getTotalOrderList(): Result<List<Order>>
}