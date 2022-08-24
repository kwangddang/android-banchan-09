package com.woowa.banchan.ui.order.detail.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderDetailHeaderBinding
import com.woowa.banchan.domain.model.Order
import java.util.*

class OrderDetailHeaderViewHolder(private val binding: ItemOrderDetailHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order) {
        binding.order = order
    }
}