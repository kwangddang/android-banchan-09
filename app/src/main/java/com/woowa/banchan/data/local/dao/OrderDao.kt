package com.woowa.banchan.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.woowa.banchan.data.local.BanchanDataBase.Companion.orderTable
import com.woowa.banchan.data.local.entity.OrderDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    fun insertOrder(orderDto: OrderDto): Long

    @Query("UPDATE $orderTable SET delivery_state = :deliverState WHERE id = :id")
    fun updateOrder(id: Long, deliverState: Boolean)

    @Delete
    fun deleteOrder(orderDto: OrderDto)

    @Query("SELECT * FROM $orderTable")
    fun getTotalOrderList(): Flow<List<OrderDto>>

    @Query("SELECT * FROM $orderTable WHERE id = :orderId")
    fun getOrder(orderId: Long): OrderDto

    @Query("SELECT EXISTS (SELECT * FROM $orderTable WHERE delivery_state = 0)")
    fun getOrderState(): Flow<Boolean>

    @Query("SELECT * FROM $orderTable")
    fun getOrderListForTest(): List<OrderDto>
}
