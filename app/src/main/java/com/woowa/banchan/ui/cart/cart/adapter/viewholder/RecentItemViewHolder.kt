package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRecentBinding
import com.woowa.banchan.domain.model.Recent

class RecentItemViewHolder(private val binding: ItemRecentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var onClickCartButton: (recent: Recent) -> Unit = {}

    fun bind(recent: Recent, isPreview: Boolean = true) {
        binding.isPreview = isPreview
        binding.recent = recent

        binding.ivCart.setOnClickListener { onClickCartButton(recent) }
    }
}