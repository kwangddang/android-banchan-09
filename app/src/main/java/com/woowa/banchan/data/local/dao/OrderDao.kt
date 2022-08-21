package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.BanchanDataBase.Companion.orderTable
import com.woowa.banchan.data.local.entity.OrderDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    fun insert(orderDto: OrderDto): Long

    @Update
    fun update(orderDto: OrderDto)

    @Delete
    fun delete(orderDto: OrderDto)

    @Query("SELECT * FROM $orderTable")
    fun getTotalOrderList(): Flow<List<OrderDto>>

    @Query("SELECT * FROM $orderTable WHERE id = :orderId")
    fun getOrder(orderId: Long): OrderDto
}
