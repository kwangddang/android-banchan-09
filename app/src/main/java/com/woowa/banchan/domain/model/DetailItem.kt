package com.woowa.banchan.domain.model

data class DetailItem(
    val hash: String,
    val deliveryFee: String,
    val deliveryInfo: String,
    val detailSection: List<String>,
    val point: String,
    val sPrice: Int,
    val nPrice: Int?,
    val percent: Int?,
    val productDescription: String,
    val thumbImages: List<String>,
    val topImage: String
) {
    fun toCart(title: String, totalCount: Int, checkState: Boolean): Cart =
        Cart(
            hash = hash,
            checkState = checkState,
            price = sPrice,
            count = totalCount,
            title = title,
            imageUrl = topImage
        )
}
