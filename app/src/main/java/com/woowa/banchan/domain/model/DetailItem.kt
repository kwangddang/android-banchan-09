package com.woowa.banchan.domain.model

data class DetailItem(
    val hash: String,
    val deliveryFee: String,
    val deliveryInfo: String,
    val detailSection: List<String>,
    val point: String,
    val prices: List<String>,
    val productDescription: String,
    val thumbImages: List<String>,
    val topImage: String
)
