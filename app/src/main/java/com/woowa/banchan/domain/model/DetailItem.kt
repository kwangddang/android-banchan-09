package com.woowa.banchan.domain.model

import java.util.*

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

    fun toRecent(title: String): Recent =
        Recent(
            hash = hash,
            time = Date(System.currentTimeMillis()),
            nPrice = nPrice,
            sPrice = sPrice,
            title = title,
            imageUrl = topImage,
        )
}
