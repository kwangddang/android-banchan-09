package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemTotalPriceBinding
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.freeShipping
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.shipping

class TotalPriceViewHolder(private val binding: ItemTotalPriceBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(totalPrice: Int) {
        binding.totalPrice = totalPrice
        binding.shipping =
            if (totalPrice >= freeShipping || totalPrice == 0) 0
            else shipping
    }
}