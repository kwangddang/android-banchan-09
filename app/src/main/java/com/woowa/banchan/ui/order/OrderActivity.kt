package com.woowa.banchan.ui.order

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityOrderBinding
import com.woowa.banchan.ui.order.adapter.OrderRVAdapter
import dagger.hilt.android.AndroidEntryPoint

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
        orderRVAdapter = OrderRVAdapter {}
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

    private fun initViewModel() {}
}