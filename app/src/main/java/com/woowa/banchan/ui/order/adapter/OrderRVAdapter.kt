package com.woowa.banchan.ui.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.databinding.ItemOrderBinding
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.ui.order.adapter.viewholder.OrderViewHolder

class OrderRVAdapter(private var orderItemClickListener: (order: Order) -> Unit) :
    ListAdapter<Order, OrderViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position)) { orderItemClickListener(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun submitOrderList(list: List<Order>) {
        submitList(list)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem.id == newItem.id
        }
    }
}