package com.woowa.banchan.domain.model

import java.util.*

data class Order(
    val id: Long,
    val time: Date,
    val count: Int,
    val price: Int,
    val title: String,
    val imageUrl: String,
)
