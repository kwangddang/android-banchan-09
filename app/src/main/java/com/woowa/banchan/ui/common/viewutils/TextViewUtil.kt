package com.woowa.banchan.ui.common.viewutils

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woowa.banchan.R
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.freeShipping
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.minimumPrice
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.shipping
import com.woowa.banchan.utils.DateUtil
import com.woowa.banchan.utils.MoneyUtil
import java.util.*

@BindingAdapter("price")
fun TextView.setPrice(price: Int) {
    text =
        MoneyUtil.getPriceFormatString(price) + resources.getString(R.string.text_view_price_format_lan)
}

@BindingAdapter("originPrice")
fun TextView.setOriginPrice(price: Int?) {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    if (price == null) {
        visibility = View.GONE
    } else {
        text =
            MoneyUtil.getPriceFormatString(price) + resources.getString(R.string.text_view_price_format_lan)
        visibility = View.VISIBLE
    }
}

@BindingAdapter("cartButtonPrice")
fun TextView.setCartButton(price: Int) {
    resources.getString(R.string.cart_preview_recent)
    text = if (price < minimumPrice) "최소주문금액을 확인해주세요"
    else {
        val p = price + if (price >= freeShipping) 0 else shipping
        val str =
            MoneyUtil.getPriceFormatString(p) + resources.getString(R.string.text_view_price_format_lan)
        str + "주문하기"
    }
}

@BindingAdapter("cartFreeShippingPrice")
fun TextView.setCartFreeShipping(price: Int) {
    text = if (price >= freeShipping) ""
    else MoneyUtil.getPriceFormatString(freeShipping - price) +
            resources.getString(R.string.text_view_price_format_lan) +
            resources.getString(R.string.text_view_notice_free_shipping)

}

@BindingAdapter("viewedDate")
fun TextView.setViewedTime(date: Date) {
    text = DateUtil.getUpdateDate(date)
}

@BindingAdapter("percent")
fun TextView.setPercent(nPrice: Int?) {
    if (nPrice == null || nPrice == 0) {
        text = ""
        visibility = View.GONE
    } else {
        text = "$nPrice%"
        visibility = View.VISIBLE
    }
}

@BindingAdapter("nPrice", "sPrice")
fun TextView.setOriginPrice(nPrice: Int?, sPrice: Int?) {
    if (nPrice == null) {
        text = if (sPrice != null)
            MoneyUtil.getPriceFormatString(sPrice) + resources.getString(R.string.text_view_price_format_lan)
        else
            ""
    } else if (sPrice == null) {
        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        text =
            MoneyUtil.getPriceFormatString(nPrice) + resources.getString(R.string.text_view_price_format_lan)
    }
}

@BindingAdapter("thumbnailTitle", "itemCount")
fun TextView.setOrderSummaryText(thumbnailTitle: String, itemCount: Int) {
    text = if (itemCount > 1) {
        resources.getString(R.string.text_view_order_summary, thumbnailTitle, itemCount)
    } else thumbnailTitle
}