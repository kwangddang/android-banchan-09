package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemCartBinding
import com.woowa.banchan.domain.model.Cart

class CartContentViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(cart: Cart) {
        if (cart.hash == "empty") {
            binding.tvEmptyNotice.isVisible = true
            binding.layoutCartContent.isGone = true
        } else
            binding.cart = cart
    }
}