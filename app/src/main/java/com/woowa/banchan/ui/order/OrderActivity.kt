package com.woowa.banchan.ui.order

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityOrderBinding
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.order.adapter.OrderRVAdapter
import com.woowa.banchan.ui.order.detail.OrderDetailActivity
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var orderRVAdapter: OrderRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)

        initViewModel()
        initAdapter()
        initToolBar()
    }

    private fun initAdapter() {
        orderRVAdapter = OrderRVAdapter {
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("order", it)
            startActivity(intent)
        }
        binding.rvOrder.adapter = orderRVAdapter
    }

    override fun onStart() {
        viewModel.getOrderList()
        super.onStart()
    }

    private fun initToolBar() {
        binding.ctbSubToolbar.setOnClickBackIcon { finish() }
        binding.ctbSubToolbar.setAppBarTitle("Order List")
    }

    private fun initViewModel() {
        viewModel.orderUiState.flowWithLifecycle(this.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> orderRVAdapter.submitOrderList(it.data)
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }
}