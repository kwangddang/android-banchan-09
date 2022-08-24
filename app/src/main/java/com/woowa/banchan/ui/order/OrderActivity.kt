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
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.key.orderId
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

        initToolBar()
        initBinding()
        initObserver()
        initAdapter()
    }

    private fun initToolBar() {
        binding.ctbSubToolbar.setAppBarTitle("Order List")
    }

    private fun initBinding() {
        binding.vm = viewModel
    }

    private fun initObserver() {
        viewModel.orderUiState.flowWithLifecycle(this.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> orderRVAdapter.submitOrderList(it.data)
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }.launchIn(lifecycleScope)

        viewModel.backClickEvent.observe(this, EventObserver { finish() })

        viewModel.orderItemClickEvent.observe(this, EventObserver {
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra(orderId, it)
            startActivity(intent)
        })
    }

    private fun initAdapter() {
        orderRVAdapter = OrderRVAdapter(viewModel.orderItemClickListener)
        binding.rvOrder.adapter = orderRVAdapter
    }

}