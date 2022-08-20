package com.woowa.banchan.ui.cart.recent.adapter

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRecentGridBinding
import com.woowa.banchan.domain.model.Recent

class RecentGridItemViewHolder(private val binding: ItemRecentGridBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        recent: Recent,
        isPreview: Boolean = true,
        onClickCartButton: (recent: Recent) -> Unit = {}
    ) {
        binding.isPreview = isPreview
        binding.recent = recent

        binding.ivCart.setOnClickListener { onClickCartButton(recent) }
    }
}