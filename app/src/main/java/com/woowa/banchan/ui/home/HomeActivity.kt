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
        initAdapter()
        initObserver()
        initTabLayoutMediator()
        initButtonSetting()
    }

    private fun initAdapter() {
        binding.vpHome.adapter = HomeVPAdapter(supportFragmentManager, lifecycle)
    }

    private fun initObserver() {
        viewModel.cartCountUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if(state is UiState.Success)
                    binding.tbHome.setCartCountIcon(state.data)
                else if(state is UiState.Error)
                    showToast(state.message)

            }.launchIn(lifecycleScope)
    }

    private fun initTabLayoutMediator() {
        val tabList = listOf("기획전", "든든한 메인요리", "뜨끈한 국물요리", "정갈한 밑반찬")

        TabLayoutMediator(binding.layoutTab, binding.vpHome) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun initButtonSetting() {
        binding.tbHome.setOnClickCartIcon {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.tbHome.setOnClickUserIcon {
            startActivity(Intent(this, OrderActivity::class.java))
        }
    }
}