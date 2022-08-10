package com.woowa.banchan.ui.common.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Base64
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ViewToolBarCustomBinding
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

class CustomToolbar(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: ViewToolBarCustomBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_tool_bar_custom,
            this,
            true
        )
    private val currentMode: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        currentMode = a.getInt(R.styleable.CustomToolbar_toolBarType, 0)

        with(binding) {
            // title 설정
            tvTitle.text = a.getString(R.styleable.CustomToolbar_toolbarTitle).toString()

            // mode에 따른 보여지는 view 설정
            ivBack.isVisible = currentMode != 0x00
            ivRefresh.isVisible = currentMode == 0x03
            layoutMainIcons.isVisible = currentMode == 0x00
            unSetUserNotifierIcon()
            setCartCountIcon(0)

            // 기본 버튼 설정
            ivBack.setOnClickListener {
                val callBackStr = a.getString(R.styleable.CustomToolbar_onClickBackIcon)
                    ?: return@setOnClickListener
                createCallBackFunc(callBackStr)()
            }
            ivUser.setOnClickListener {
                val callBackStr = a.getString(R.styleable.CustomToolbar_onClickUserIcon)
                    ?: return@setOnClickListener
                createCallBackFunc(callBackStr)()
            }
            ivCart.setOnClickListener {
                val callBackStr = a.getString(R.styleable.CustomToolbar_onClickCartIcon)
                    ?: return@setOnClickListener
                createCallBackFunc(callBackStr)()
            }
            ivRefresh.setOnClickListener {
                val callBackStr = a.getString(R.styleable.CustomToolbar_onClickRefreshIcon)
                    ?: return@setOnClickListener
                createCallBackFunc(callBackStr)()
            }

        }
        a.recycle()
    }

    fun setOnClickBackIcon(c: () -> Unit) = binding.ivBack.setOnClickListener { c() }
    fun setOnClickCartIcon(c: () -> Unit) = binding.ivCart.setOnClickListener { c() }
    fun setOnClickUserIcon(c: () -> Unit) = binding.ivUser.setOnClickListener { c() }
    fun setOnClickRefreshIcon(c: () -> Unit) = binding.ivRefresh.setOnClickListener { c() }

    fun setUserNotifierIcon() {
        if (currentMode == 0x00) binding.ivUserBlackDot.isVisible = true
    }

    fun unSetUserNotifierIcon() {
        if (currentMode == 0x00) binding.ivUserBlackDot.isGone = true
    }

    fun setCartCountIcon(count: Int) {
        if (currentMode == 0x00)
            with(binding.tvCartCount) {
                this.isVisible = count > 0
                this.text = count.toString()
            }
    }

    fun setAppBarTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun getCurrentMode(): Int = currentMode

    private fun createCallBackFunc(callBackStr: String): () -> Unit {
        val decoded = Base64.decode(callBackStr, Base64.DEFAULT)

        return ObjectInputStream(ByteArrayInputStream(decoded))
            .readObject() as (() -> Unit)
    }
}