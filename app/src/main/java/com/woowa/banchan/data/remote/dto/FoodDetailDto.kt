package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.DetailItem

data class FoodDetailDto(
    @SerializedName("data")
    val data: DetailItemDto,
    @SerializedName("hash")
    val hash: String
) {
    fun toFoodDetail(): DetailItem =
        DetailItem(
            hash = hash,
            deliveryFee = data.deliveryFee,
            deliveryInfo = data.deliveryInfo,
            detailSection = data.detailSection,
            point = data.point,
            prices = data.prices,
            productDescription = data.productDescription,
            thumbImages = data.thumbImages,
            topImage = data.topImage
        )
}