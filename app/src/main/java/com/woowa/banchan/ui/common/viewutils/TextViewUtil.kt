package com.woowa.banchan.ui.common.viewutils

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woowa.banchan.ui.cart.cart.CartFragment
import com.woowa.banchan.utils.DateUtil
import com.woowa.banchan.utils.StringUtil
import java.util.*

@BindingAdapter("price")
fun TextView.setPrice(price: Int) {
    text = StringUtil.getMoneyFormatString(price)
}

@BindingAdapter("originPrice")
fun TextView.setOriginPrice(price: Int) {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    text = StringUtil.getMoneyFormatString(price)
}

@BindingAdapter("cartButtonPrice")
fun TextView.setCartButton(price: Int) {
    text = if (price <= 0) "최소주문금액을 확인해주세요"
    else {
        val p = price + if (price >= CartFragment.freeShipping) 0 else CartFragment.shipping
        val str = StringUtil.getMoneyFormatString(p)
        "$str 주문하기"
    }
}

@BindingAdapter("cartFreeShippingPrice")
fun TextView.setCartFreeShipping(price: Int) {
    text = if (price <= 0) ""
    else StringUtil.getMoneyFormatString(price) + "을 더 담으면 무료배송!"
}

@BindingAdapter("viewedDate")
fun TextView.setViewedTime(date: Date) {
    text = DateUtil.getUpdateDate(date)
}