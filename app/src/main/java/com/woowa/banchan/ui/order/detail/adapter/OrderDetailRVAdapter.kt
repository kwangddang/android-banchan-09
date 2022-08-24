package com.woowa.banchan.ui.order.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderDetailBinding
import com.woowa.banchan.databinding.ItemOrderDetailHeaderBinding
import com.woowa.banchan.databinding.ItemTotalPriceBinding
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.ui.cart.cart.adapter.viewholder.TotalPriceViewHolder
import com.woowa.banchan.ui.home.RVItem
import com.woowa.banchan.ui.order.detail.ORDER_CONTENT
import com.woowa.banchan.ui.order.detail.ORDER_HEADER
import com.woowa.banchan.ui.order.detail.ORDER_TOTAL_PRICE
import com.woowa.banchan.ui.order.detail.adapter.viewholder.OrderDetailHeaderViewHolder
import com.woowa.banchan.ui.order.detail.adapter.viewholder.OrderDetailViewHolder

class OrderDetailRVAdapter() : ListAdapter<RVItem, RecyclerView.ViewHolder>(diffUtil) {

    private var totalPrice = 0
    private var orderDetailHeaderViewHolder: OrderDetailHeaderViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ORDER_HEADER -> {
                orderDetailHeaderViewHolder = OrderDetailHeaderViewHolder(
                    ItemOrderDetailHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
                orderDetailHeaderViewHolder!!
            }
            ORDER_CONTENT -> OrderDetailViewHolder(
                ItemOrderDetailBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> TotalPriceViewHolder(
                ItemTotalPriceBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ORDER_HEADER -> (holder as OrderDetailHeaderViewHolder).bind(
                (getItem(0) as RVItem.Item<Order>).item
            )
            ORDER_CONTENT -> (holder as OrderDetailViewHolder).bind((getItem(position) as RVItem.Item<OrderItem>).item)
            else -> (holder as TotalPriceViewHolder).bind(totalPrice)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val lastIdx = itemCount - 1

        return when (position) {
            0 -> ORDER_HEADER
            lastIdx -> ORDER_TOTAL_PRICE
            else -> ORDER_CONTENT
        }
    }

    fun submitOrderAndItem(order: Order, list: List<OrderItem>) {
        val newList = mutableListOf<RVItem>()

        totalPrice = 0

        newList.add(RVItem.Item(order))
        list.forEach {
            totalPrice += (it.price * it.count)
            newList.add(RVItem.Item(it))
        }
        newList.add(RVItem.Footer)

        submitList(newList)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RVItem>() {
            override fun areItemsTheSame(oldItem: RVItem, newItem: RVItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RVItem, newItem: RVItem): Boolean =
                oldItem == newItem
        }
    }
}