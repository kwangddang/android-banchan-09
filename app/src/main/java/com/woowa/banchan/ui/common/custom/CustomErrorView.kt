package com.woowa.banchan.ui.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ViewErrorCustomBinding

class CustomErrorView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: ViewErrorCustomBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_error_custom,
            this,
            true
        )

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomErrorView)
        a.recycle()
    }

    fun setOnClickRefreshButton(c: (() -> Unit)? = null) =
        binding.btnRefresh.setOnClickListener { c?.invoke() }
}