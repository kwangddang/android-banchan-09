package com.woowa.banchan.domain.model

class Cart(
    val hash: String,
    val checkState: Boolean,
    val price: Int,
    val count: Int,
    val title: String,
    val imageUrl: String,
)