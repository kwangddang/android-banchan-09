package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.utils.MoneyUtil

data class FoodItemDto(
    @SerializedName("alt")
    val alt: String,
    @SerializedName("badge")
    val badge: List<String>,
    @SerializedName("delivery_type")
    val deliveryType: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("detail_hash")
    val detailHash: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("n_price")
    val nPrice: String?,
    @SerializedName("s_price")
    val sPrice: String,
    @SerializedName("title")
    val title: String
) {
    fun toFoodItem(): FoodItem {
        val salePrice = if (nPrice == null) null else MoneyUtil.getIntegerMoney(nPrice)
        val originPrice = MoneyUtil.getIntegerMoney(sPrice)
        return FoodItem(
            description = description,
            detailHash = detailHash,
            image = image,
            nPrice = salePrice,
            sPrice = originPrice,
            percent = if (nPrice == null) null else (((salePrice!! - originPrice) * 100) / salePrice),
            title = title
        )
    }
}