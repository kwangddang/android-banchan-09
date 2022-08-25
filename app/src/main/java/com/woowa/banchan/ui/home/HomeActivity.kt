package com.woowa.banchan.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityHomeBinding
import com.woowa.banchan.ui.cart.CartActivity
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.home.adapter.HomeVPAdapter
import com.woowa.banchan.ui.order.OrderActivity
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Banchan)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initBinding()
        initAdapter()
        initObserver()
        initTabLayoutMediator()
    }

    private fun initBinding() {
        binding.vm = viewModel
    }

    private fun initAdapter() {
        binding.vpHome.adapter = HomeVPAdapter(supportFragmentManager, lifecycle)
    }

    private fun initObserver() {
        viewModel.cartCountUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if (state is UiState.Success)
                    binding.tbHome.setCartCountIcon(state.data)
                else if (state is UiState.Error)
                    showToast(state.error.message)

            }.launchIn(lifecycleScope)

        viewModel.orderStateUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    if (state.data)
                        binding.tbHome.setUserNotifierIcon()
                    else
                        binding.tbHome.unSetUserNotifierIcon()
                } else if (state is UiState.Error)
                    showToast(state.error.message)

            }.launchIn(lifecycleScope)

        viewModel.cartClickEvent.observe(this, EventObserver {
            startActivity(Intent(this, CartActivity::class.java))
        })

        viewModel.userClickEvent.observe(this, EventObserver {
            startActivity(Intent(this, OrderActivity::class.java))
        })
    }

    private fun initTabLayoutMediator() {
        val tabList = listOf(
            getString(R.string.home_tab_best),
            getString(R.string.home_tab_main),
            getString(R.string.home_tab_soup),
            getString(R.string.home_tab_side)
        )

        TabLayoutMediator(binding.layoutTab, binding.vpHome) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

}