package com.woowa.banchan.ui.home.best

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("nPricePercent", "sPricePercent")
fun TextView.setPercent(nPrice: String?, sPrice: String) {
    if(nPrice == null) {
        text = ""
    }
    else {
        val originPrice = nPrice.replace("원", "").replace(",", "").toLong()
        val salePrice = sPrice.replace("원", "").replace(",", "").toLong()
        val percent = ((originPrice - salePrice) / originPrice) * 100
        text = "$percent%"
    }
}

@BindingAdapter("nPrice", "sPrice")
fun TextView.setOriginPrice(nPrice: String?, sPrice: String) {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    if(nPrice == null)
        text = ""
    else
        text = sPrice
}