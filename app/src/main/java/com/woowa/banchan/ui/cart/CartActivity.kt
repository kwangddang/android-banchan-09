package com.woowa.banchan.ui.cart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.woowa.banchan.R
import com.woowa.banchan.ui.cart.cart.CartFragment
import com.woowa.banchan.ui.cart.recent.RecentFragment
import com.woowa.banchan.ui.common.custom.CustomToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    lateinit var toolbar: CustomToolbar

    private val viewModel by viewModels<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        toolbar = findViewById(R.id.ctb_sub_toolbar)

        initViewModel()
        initButtonSetting()
    }

    private fun initButtonSetting() {
        toolbar.setOnClickBackIcon { onBackPressed() }
    }

    private fun initViewModel() {
        viewModel.fragmentTag.observe(this) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fcv_main,
                    when (it) {
                        getString(R.string.fragment_cart) -> CartFragment()
                        else -> RecentFragment()
                    }
                ).apply { if (it == getString(R.string.fragment_recent)) addToBackStack(it) }
                .commitAllowingStateLoss()
            toolbar.setAppBarTitle(
                when (it) {
                    getString(R.string.fragment_cart) -> "Cart"
                    else -> "Recently viewed products"
                }
            )
        }
    }


    override fun onBackPressed() {
        val currentFragment = viewModel.fragmentTag.getValue()
        currentFragment?.apply {
            if (this == getString(R.string.fragment_cart)) finish()
            else viewModel.setFragmentTag(getString(R.string.fragment_cart))
        }
    }
}