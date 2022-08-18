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

    private var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkOrderIsNull()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)

        initViewModel()
        initButton()
        initAdapter()
    }

    private fun checkOrderIsNull() {
        order = intent.getSerializableExtra("order") as Order?
        if (order == null) finish()
    }

    private fun initViewModel() {
        viewModel.getOrderDetail(order!!.id)
        viewModel.orderItemUiState.flowWithLifecycle(this.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> orderDetailRVAdapter.submitOrderItemList(it.data)
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }.launchIn(lifecycleScope)

        viewModel.orderUiState.flowWithLifecycle(this.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        this.order = it.data
                        orderDetailRVAdapter.submitOrderItem(it.data)
                    }
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun initAdapter() {
        orderDetailRVAdapter = OrderDetailRVAdapter(order!!)
        binding.rvOrderDetail.adapter = orderDetailRVAdapter
    }

    private fun initButton() {
        binding.ctbSubToolbar.setOnClickBackIcon { finish() }
        binding.ctbSubToolbar.setOnClickRefreshIcon { viewModel.getOrder(order!!.id) }
    }
}