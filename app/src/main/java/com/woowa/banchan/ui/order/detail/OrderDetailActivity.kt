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
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.key.orderId
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.order.detail.adapter.OrderDetailRVAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel by viewModels<OrderDetailViewModel>()
    private lateinit var orderDetailRVAdapter: OrderDetailRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOrderIsNull()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)

        initBinding()
        initObserver()
        initViews()
        initAdapter()
    }

    private fun initBinding() {
        binding.vm = viewModel
    }

    private fun checkOrderIsNull() {
        viewModel.order = intent.getSerializableExtra(orderId) as Order?
        if (viewModel.order == null) finish()
    }

    private fun initObserver() {
        viewModel.orderItemUiState.flowWithLifecycle(this.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> orderDetailRVAdapter.submitOrderItemList(it.data)
                    is UiState.Error -> showToast(it.error.throwable.message)
                    else -> {}
                }
            }.launchIn(lifecycleScope)

        viewModel.orderUiState.flowWithLifecycle(this.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        viewModel.order = it.data
                        orderDetailRVAdapter.submitOrderItem(it.data)
                    }
                    is UiState.Error -> showToast(it.error.throwable.message)
                    else -> {}
                }
            }.launchIn(lifecycleScope)

        viewModel.backClickEvent.observe(this, EventObserver { finish() })
    }

    private fun initViews() {
        viewModel.getOrderDetail()
    }

    private fun initAdapter() {
        orderDetailRVAdapter = OrderDetailRVAdapter(viewModel.order!!)
        binding.rvOrderDetail.adapter = orderDetailRVAdapter
    }

}