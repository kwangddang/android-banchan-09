package com.woowa.banchan.ui.cart.recent.adapter

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRecentGridBinding
import com.woowa.banchan.domain.model.Recent

class RecentGridItemViewHolder(private val binding: ItemRecentGridBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        recent: Recent,
        onClickItem: (recent: Recent) -> Unit,
        onClickCartButton: (recent: Recent) -> Unit = {},
        onClickCheckButton: (recent: Recent) -> Unit = {}
    ) {
        binding.recent = recent
        binding.checkState = recent.checkState
        binding.ivCart.setOnClickListener { onClickCartButton(recent) }
        binding.ivCheck.setOnClickListener { onClickCheckButton(recent) }
        binding.root.setOnClickListener { onClickItem(recent) }
    }

    fun bind(checkState: Boolean) {
        binding.checkState = checkState
        binding.recent!!.checkState = checkState
    }
}