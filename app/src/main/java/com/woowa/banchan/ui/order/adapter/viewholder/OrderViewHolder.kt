package com.woowa.banchan.ui.order.adapter.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderBinding
import com.woowa.banchan.domain.model.Order

class OrderViewHolder(private val binding: ItemOrderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order, onClick: (order: Order) -> Unit = {}) {
        binding.order = order
        binding.layoutOrderRoot.setOnClickListener { onClick(order) }
    }
}