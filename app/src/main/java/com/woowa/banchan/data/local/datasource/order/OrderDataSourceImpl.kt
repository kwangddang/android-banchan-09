package com.woowa.banchan.data.local.datasource.order

import android.util.Log
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.dao.OrderItemDao
import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao,
    private val database: BanchanDataBase
) : OrderDataSource {

    override suspend fun getTotalOrderList(): Result<Flow<List<OrderDto>>> =
        runCatching {
            Log.d("Test","getTotalList")
            orderDao.getTotalOrderList()
        }

    override suspend fun getOrderDetail(orderId: Long): Result<List<OrderItemDto>> =
        runCatching { orderItemDao.getOrderDetail(orderId) }

    override suspend fun getOrder(orderId: Long): Result<OrderDto> =
        runCatching { orderDao.getOrder(orderId) }

    override suspend fun insertNewOrder(orderDto: OrderDto): Result<Long> =
        runCatching { orderDao.insertOrder(orderDto) }

    override suspend fun insertNewOrderItem(orderItemDto: List<OrderItemDto>): Result<Unit> =
        runCatching { orderItemDao.insert(*orderItemDto.toTypedArray()) }

    override suspend fun insertNewOrderAndItem(
        newOrder: OrderDto,
        orderItemList: List<OrderItemDto>
    ): Result<OrderDto> =
        runCatching {
            var orderId = 0L
            database.runInTransaction {
                orderId = orderDao.insertOrder(newOrder)
                orderItemList.forEach { it.orderId = orderId }
                orderItemDao.insert(*orderItemList.toTypedArray())
            }
            orderDao.getOrder(orderId)
        }

    override suspend fun updateOrder(id:Long, deliverState: Boolean): Result<Unit> =
        runCatching { orderDao.updateOrder(id, deliverState) }

}