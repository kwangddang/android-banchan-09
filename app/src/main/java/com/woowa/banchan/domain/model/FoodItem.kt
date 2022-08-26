package com.woowa.banchan.domain.model

data class FoodItem(
    val description: String,
    val detailHash: String,
    val image: String,
    val nPrice: Int?,
    val sPrice: Int,
    val percent: Int?,
    val title: String,
    var checkState: Boolean = false
) {

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
