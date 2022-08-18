package com.woowa.banchan.domain.model

import com.woowa.banchan.data.local.entity.RecentDto
import java.util.*

data class Recent(
    val hash: String,
    val time: Date,
    val nPrice: Int,
    val sPrice: Int,
    val title: String,
    val imageUrl: String,
)

fun Recent.toRecentDto(): RecentDto =
    RecentDto(
        hash,
        time,
        nPrice,
        sPrice,
        title,
        imageUrl
    )