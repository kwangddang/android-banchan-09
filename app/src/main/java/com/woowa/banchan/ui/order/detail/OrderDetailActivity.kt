package com.woowa.banchan.ui.order.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityOrderDetailBinding
import com.woowa.banchan.ui.order.detail.adapter.OrderDetailRVAdapter

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var orderDetailRVAdapter: OrderDetailRVAdapter

    private val orderId: Int by lazy {
        intent.getIntExtra("orderId", -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (orderId == -1) finish()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)

        initButton()
        initAdapter()
    }

    private fun initAdapter() {
        orderDetailRVAdapter = OrderDetailRVAdapter()
        binding.rvOrderDetail.adapter = orderDetailRVAdapter
    }

    private fun initButton() {
        binding.ctbSubToolbar.setOnClickBackIcon { finish() }
        binding.ctbSubToolbar.setOnClickRefreshIcon { }
    }
}