package com.woowa.banchan.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.utils.MoneyUtil
import com.woowa.banchan.domain.model.FoodDetail

data class FoodDetailDto(
    @SerializedName("data")
    val data: DetailItemDto,
    @SerializedName("hash")
    val hash: String
) {
    fun toFoodDetail(): DetailItem {
        val priceList = data.prices
        val nPrice: Int?
        val sPrice: Int
        if (priceList.size > 1) {
            nPrice = MoneyUtil.getIntegerMoney(priceList[0])
            sPrice = MoneyUtil.getIntegerMoney(priceList[1])
        } else {
            nPrice = null
            sPrice = MoneyUtil.getIntegerMoney(priceList[0])
        }
        return DetailItem(
            hash = hash,
            deliveryFee = data.deliveryFee,
            deliveryInfo = data.deliveryInfo,
            detailSection = data.detailSection,
            point = data.point,
            nPrice = nPrice,
            sPrice = sPrice,
            percent = if (nPrice == null) null else (((nPrice - sPrice) * 100) / nPrice),
            productDescription = data.productDescription,
            thumbImages = data.thumbImages,
            topImage = data.topImage
        )
    }
}