package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.BanchanDataBase.Companion.orderTable
import com.woowa.banchan.data.local.entity.OrderDto

@Dao
interface OrderDao {

    @Insert
    fun insert(orderDto: OrderDto)

    @Update
    fun update(orderDto: OrderDto)

    @Delete
    fun delete(orderDto: OrderDto)

    @Query("SELECT * FROM $orderTable")
    fun getTotalOrderList(): List<OrderDto>
}
