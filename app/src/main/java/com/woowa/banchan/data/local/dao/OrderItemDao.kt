package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.BanchanDataBase.Companion.orderItemTable
import com.woowa.banchan.data.local.entity.OrderItemDto

@Dao
interface OrderItemDao {

    @Insert
    fun insert(orderItemDto: OrderItemDto)

    @Update
    fun update(orderItemDto: OrderItemDto)

    @Delete
    fun delete(orderItemDto: OrderItemDto)

    @Query("SELECT * FROM $orderItemTable WHERE order_id = :orderId")
    fun getOrderDetail(orderId: Long): List<OrderItemDto>
}
