package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.DetailItem

data class DetailItemDto(
    @SerializedName("delivery_fee")
    val deliveryFee: String,
    @SerializedName("delivery_info")
    val deliveryInfo: String,
    @SerializedName("detail_section")
    val detailSection: List<String>,
    @SerializedName("point")
    val point: String,
    @SerializedName("prices")
    val prices: List<String>,
    @SerializedName("product_description")
    val productDescription: String,
    @SerializedName("thumb_images")
    val thumbImages: List<String>,
    @SerializedName("top_image")
    val topImage: String
) {
    fun toDetailItem() =
        DetailItem(
            deliveryFee = deliveryFee,
            deliveryInfo = deliveryInfo,
            detailSection = detailSection,
            point = point,
            prices = prices,
            productDescription = productDescription,
            thumbImages = thumbImages,
            topImage = topImage
        )
}