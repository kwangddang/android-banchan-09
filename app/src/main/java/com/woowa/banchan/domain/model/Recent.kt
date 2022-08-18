package com.woowa.banchan.domain.model

import java.util.*

data class Recent(
    val hash: String,
    val time: Date,
    val nPrice: Int?,
    val sPrice: Int,
    val title: String,
    val imageUrl: String,
)