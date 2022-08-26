package com.woowa.banchan.domain.model

import java.io.Serializable
import java.util.*

data class Order(
    val id: Long,
    val deliveryState: Boolean,
    val time: Date,
    val count: Int,
    val price: Int,
    val title: String,
    val imageUrl: String,
) : Serializable
