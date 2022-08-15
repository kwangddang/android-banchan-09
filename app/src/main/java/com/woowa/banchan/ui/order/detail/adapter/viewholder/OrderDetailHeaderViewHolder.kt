package com.woowa.banchan.ui.order.detail.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemOrderDetailHeaderBinding
import java.util.*

class OrderDetailHeaderViewHolder(private val binding: ItemOrderDetailHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(count: Int, state: Boolean, time: Date) {
        binding.itemCount = count
        binding.deliveryState = state
        val leftTime = (System.currentTimeMillis() - time.time) / 60000
        binding.leftTime = leftTime.toInt()
    }
}