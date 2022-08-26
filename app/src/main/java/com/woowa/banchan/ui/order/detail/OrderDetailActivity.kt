package com.woowa.banchan.ui.order.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityOrderDetailBinding
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.key.ORDER_ID
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.order.detail.adapter.OrderDetailRVAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel by viewModels<OrderDetailViewModel>()
    private val orderDetailRVAdapter: OrderDetailRVAdapter by lazy {
        OrderDetailRVAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOrderIsNull()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)

        initBinding()
        initObserver()
        initAdapter()
    }

    private fun initBinding() {
        binding.vm = viewModel
    }

    private fun checkOrderIsNull() {
        viewModel.orderId = intent.getLongExtra(ORDER_ID, 0L)
        if (viewModel.orderId == 0L) finish()
    }

    private fun initObserver() {
        viewModel.orderState.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.first is UiState.Success && it.second is UiState.Success)
                    orderDetailRVAdapter.submitOrderAndItem(
                        (it.first as UiState.Success<Order>).data,
                        (it.second as UiState.Success<List<OrderItem>>).data
                    )
            }.launchIn(lifecycleScope)

        viewModel.refreshClickEvent.observe(this, EventObserver { orderDetailRVAdapter.refreshTime() })

        viewModel.backClickEvent.observe(this, EventObserver { finish() })
    }

    private fun initAdapter() {
        binding.rvOrderDetail.adapter = orderDetailRVAdapter
    }

}