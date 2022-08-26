package com.woowa.banchan.ui.cart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityCartBinding
import com.woowa.banchan.ui.cart.cart.CartFragment
import com.woowa.banchan.ui.cart.recent.RecentFragment
import com.woowa.banchan.ui.common.event.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private val viewModel by viewModels<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        initBinding()
        initObserver()
    }

    private fun initBinding() {
        binding.vm = viewModel
    }

    private fun initObserver() {
        viewModel.fragmentTag.observe(this) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fcv_main,
                    when (it) {
                        getString(R.string.fragment_cart) -> CartFragment()
                        else -> RecentFragment()
                    }
                ).apply { if (it == getString(R.string.fragment_recent)) addToBackStack(it) }
                .commitAllowingStateLoss()

            binding.ctbSubToolbar.setAppBarTitle(
                when (it) {
                    getString(R.string.fragment_cart) -> getString(R.string.toolbar_title_cart)
                    else -> getString(R.string.toolbar_title_cart)
                }
            )
        }

        viewModel.backClickEvent.observe(this, EventObserver {
            val currentFragment = viewModel.fragmentTag.getValue()
            currentFragment?.apply {
                if (this == getString(R.string.fragment_cart)) finish()
                else viewModel.setFragmentTag(getString(R.string.fragment_cart))
            }
        })
    }

}