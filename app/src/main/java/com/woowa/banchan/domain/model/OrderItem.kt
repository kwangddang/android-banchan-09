package com.woowa.banchan.domain.model

data class OrderItem(
    val id: Long,
    val orderId: Long,
    val count: Int,
    val price: Int,
    val title: String,
    val imageUrl: String,
)
