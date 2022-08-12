package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woowa.banchan.data.local.BanchanDataBase.Companion.orderItemTable

@Entity(tableName = orderItemTable)
data class OrderItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "order_id") val orderId: Long,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
)
