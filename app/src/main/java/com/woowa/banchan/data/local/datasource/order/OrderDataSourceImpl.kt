package com.woowa.banchan.data.local.datasource.order

import androidx.annotation.WorkerThread
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.dao.OrderItemDao
import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao,
    private val database: BanchanDataBase
) : OrderDataSource {

    override suspend fun getTotalOrderList(): Flow<List<OrderDto>> =
        orderDao.getTotalOrderList()

    override suspend fun getOrderDetail(orderId: Long): List<OrderItemDto> =
        orderItemDao.getOrderDetail(orderId)

    @WorkerThread
    override suspend fun getOrder(orderId: Long): Flow<OrderDto> =
        orderDao.getOrder(orderId)

    override suspend fun insertNewOrder(orderDto: OrderDto): Long =
        orderDao.insertOrder(orderDto)

    override suspend fun insertNewOrderItem(orderItemDto: List<OrderItemDto>) =
        orderItemDao.insert(*orderItemDto.toTypedArray())

    override suspend fun insertNewOrderAndItem(
        newOrder: OrderDto,
        orderItemList: List<OrderItemDto>
    ): Long =
        withContext(Dispatchers.IO) {
            var orderId = 0L
            database.runInTransaction {
                orderId = orderDao.insertOrder(newOrder)
                orderItemList.forEach { it.orderId = orderId }
                orderItemDao.insert(*orderItemList.toTypedArray())
            }
            orderId
        }


    override suspend fun updateOrder(id: Long, deliverState: Boolean) =
        orderDao.updateOrder(id, deliverState)

    override suspend fun getOrderState(): Flow<Boolean> =
        orderDao.getOrderState()

}