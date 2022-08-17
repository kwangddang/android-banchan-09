package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woowa.banchan.data.local.BanchanDataBase.Companion.orderTable
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import java.util.*

@Entity(tableName = orderTable)
data class OrderDto(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "time") val time: Date,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
)

fun OrderDto.toOrder(): Order {
    return Order(
        id = id!!,
        deliveryState = (System.currentTimeMillis() - time.time < (60000 * 20)),
        time = time,
        count = count,
        price = price,
        title = title,
        imageUrl = imageUrl
    )
}

fun newOrderDto(count: Int, price: Int, thumbnailItem: Cart): OrderDto = OrderDto(
    id = null,
    time = Date(System.currentTimeMillis()),
    count = count,
    price = price,
    title = thumbnailItem.title,
    imageUrl = thumbnailItem.imageUrl,
)