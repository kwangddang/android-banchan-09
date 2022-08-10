package com.woowa.banchan.ui.home.best

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("nPrice", "sPrice")
fun TextView.setPercent(nPrice: String, sPrice: String) {
    val originPrice = nPrice.replace("원", "").replace(",", "").toLong()
    val salePrice = sPrice.replace("원", "").replace(",", "").toLong()
    val percent = ((originPrice - salePrice) / originPrice) * 100
    text = "$percent%"
}

@BindingAdapter("originPrice")
fun TextView.setOriginPrice(originPrice: String) {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    text = originPrice
}