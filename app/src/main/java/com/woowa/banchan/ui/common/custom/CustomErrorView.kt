package com.woowa.banchan.ui.common.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Base64
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ViewErrorCustomBinding
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

class CustomErrorView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: ViewErrorCustomBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_tool_bar_custom,
            this,
            true
        )

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomErrorView)

        binding.btnRefresh.setOnClickListener {
            val callBackStr = a.getString(R.styleable.CustomErrorView_onClickRefreshButton)
                ?: return@setOnClickListener
            createCallBackFunc(callBackStr)()
        }
        a.recycle()
    }

    fun setOnClickRefreshButton(c: () -> Unit) = binding.btnRefresh.setOnClickListener { c() }

    private fun createCallBackFunc(callBackStr: String): () -> Unit {
        val decoded = Base64.decode(callBackStr, Base64.DEFAULT)

        return ObjectInputStream(ByteArrayInputStream(decoded))
            .readObject() as (() -> Unit)
    }
}