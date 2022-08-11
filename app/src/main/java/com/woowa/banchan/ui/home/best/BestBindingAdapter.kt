package com.woowa.banchan.ui.home.best

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("nPricePercent", "sPricePercent")
fun TextView.setPercent(nPrice: String?, sPrice: String) {
    if (nPrice == null) {
        text = ""
        visibility = View.GONE
    } else {
        val originPrice = nPrice.replace("원", "").replace(",", "").toLong()
        val salePrice = sPrice.replace("원", "").replace(",", "").toLong()
        val percent = (((originPrice - salePrice) * 100) / originPrice)
        text = "$percent%"
    }
}

@BindingAdapter("nPrice", "sPrice")
fun TextView.setOriginPrice(nPrice: String?, sPrice: String) {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    if (nPrice == null)
        text = ""
    else
        text = nPrice
}

@BindingAdapter("image")
fun ImageView.setImage(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}