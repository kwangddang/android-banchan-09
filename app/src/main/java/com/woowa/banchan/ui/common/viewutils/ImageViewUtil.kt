package com.woowa.banchan.ui.common.viewutils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.woowa.banchan.R

@BindingAdapter("image")
fun ImageView.setImage(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("cartIcon")
fun ImageView.setCartIcon(isCart: Boolean) {
    if(isCart) {
        setBackgroundResource(R.drawable.background_circle_accent)
        setImageResource(R.drawable.ic_check_white)
    } else {
        setBackgroundResource(R.drawable.background_circle_white)
        setImageResource(R.drawable.ic_cart)
    }
}