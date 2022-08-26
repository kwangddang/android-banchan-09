package com.woowa.banchan.domain.model

import java.util.*

data class Recent(
    val hash: String,
    val time: Date,
    val nPrice: Int?,
    val sPrice: Int,
    val title: String,
    val imageUrl: String,
    var checkState: Boolean = false
) {

    fun toFoodItem(): FoodItem = FoodItem(
        description = "",
        detailHash = hash,
        image = imageUrl,
        nPrice = nPrice,
        sPrice = sPrice,
        percent = if (nPrice == null) null else (((nPrice - sPrice) * 100) / nPrice),
        title = title
    )
}