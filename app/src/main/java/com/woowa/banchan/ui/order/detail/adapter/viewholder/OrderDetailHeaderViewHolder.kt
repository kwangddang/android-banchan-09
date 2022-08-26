package com.woowa.banchan.ui.order.detail.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderDetailHeaderBinding
import com.woowa.banchan.domain.model.Order

class OrderDetailHeaderViewHolder(private val binding: ItemOrderDetailHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order) {
        binding.order = order
        binding.time = order.time
    }

    fun refresh() {
        binding.time = binding.order!!.time
    }
}