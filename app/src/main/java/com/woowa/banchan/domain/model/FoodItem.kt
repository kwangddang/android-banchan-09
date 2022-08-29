package com.woowa.banchan.domain.model

import java.io.Serializable

data class FoodItem(
    val description: String,
    val detailHash: String,
    val image: String,
    val nPrice: Int?,
    val sPrice: Int,
    val percent: Int?,
    val title: String,
    var checkState: Boolean = false
) : Serializable {

    fun toCart(totalCount: Int, checkState: Boolean): Cart =
        Cart(
            hash = detailHash,
            checkState = checkState,
            price = sPrice,
            count = totalCount,
            title = title,
            imageUrl = image
        )
}
