package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woowa.banchan.data.local.BanchanDataBase.Companion.cartTable
import com.woowa.banchan.domain.model.Cart

@Entity(tableName = cartTable)
data class CartDto(
    @PrimaryKey val hash: String,
    @ColumnInfo(name = "check_state") val checkState: Boolean,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
)

fun CartDto.toCart(): Cart = Cart(
    hash = hash,
    checkState = checkState,
    price = price,
    count = count,
    title = title,
    imageUrl = imageUrl
)

fun CartDto.toOrderItemDto(orderId: Long): OrderItemDto = OrderItemDto(
    id = -1,
    orderId = orderId,
    price = price,
    count = count,
    title = title,
    imageUrl = imageUrl
)

fun Cart.toCartDto() = CartDto(
    hash = hash,
    checkState = checkState,
    price = price,
    count = count,
    title = title,
    imageUrl = imageUrl
)