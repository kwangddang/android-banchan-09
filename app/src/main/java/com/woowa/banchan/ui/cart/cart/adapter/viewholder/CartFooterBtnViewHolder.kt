package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemCartButtonFooterBinding
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.minimumPrice

class CartFooterBtnViewHolder(private val binding: ItemCartButtonFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var onClickOrderButton: () -> Unit = {}

    fun bind(totalPrice: Int) {
        binding.totalPrice = totalPrice
        binding.minimumPrice = minimumPrice

        binding.tvOrderBtn.setOnClickListener { onClickOrderButton() }
    }
}