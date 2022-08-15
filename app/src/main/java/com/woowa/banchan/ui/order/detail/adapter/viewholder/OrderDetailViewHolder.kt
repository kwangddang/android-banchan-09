package com.woowa.banchan.ui.order.detail.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderDetailBinding
import com.woowa.banchan.domain.model.OrderItem

class OrderDetailViewHolder(private val binding: ItemOrderDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(orderItem: OrderItem) {
        binding.order = orderItem
    }
}