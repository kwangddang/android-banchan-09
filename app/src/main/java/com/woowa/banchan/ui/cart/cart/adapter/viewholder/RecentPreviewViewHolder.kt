package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemRecentPreviewBinding
import com.woowa.banchan.ui.cart.cart.adapter.RecentPreviewRVAdapter

class RecentPreviewViewHolder(private val binding: ItemRecentPreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(categoryFood: List<FoodItem>) {
        val adapter = RecentPreviewRVAdapter()
        adapter.submitList(categoryFood)
        binding.rvRecentPreview.adapter = adapter
    }
}