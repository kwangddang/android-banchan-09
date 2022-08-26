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
            .placeholder(R.drawable.placeholder)
            .into(this)
    }
}