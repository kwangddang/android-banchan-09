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
)
