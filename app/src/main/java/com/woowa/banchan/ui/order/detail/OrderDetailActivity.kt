package com.woowa.banchan.ui.order.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityOrderDetailBinding
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.order.detail.adapter.OrderDetailRVAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel by viewModels<OrderDetailViewModel>()
    private lateinit var orderDetailRVAdapter: OrderDetailRVAdapter

    private val order: Order? by lazy {
        intent.getIntExtra("orderId", -1)
        null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (order == null) finish()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)

        initViewModel()
        initButton()
        initAdapter()
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
            }
    }

    private fun initAdapter() {
        orderDetailRVAdapter = OrderDetailRVAdapter(order!!)
        binding.rvOrderDetail.adapter = orderDetailRVAdapter
    }

    private fun initButton() {
        binding.ctbSubToolbar.setOnClickBackIcon { finish() }
        binding.ctbSubToolbar.setOnClickRefreshIcon { }
    }
}