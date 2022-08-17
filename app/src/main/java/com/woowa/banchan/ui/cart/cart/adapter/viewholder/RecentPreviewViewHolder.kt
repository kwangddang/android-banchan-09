package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRecentPreviewBinding
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.cart.adapter.RecentPreviewRVAdapter

class RecentPreviewViewHolder(
    private val binding: ItemRecentPreviewBinding,
    private val onClickAllRecentlyViewed: () -> Unit = {}
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(recentList: List<Recent>) {
        binding.tvEmptyNotice.isVisible = recentList.isEmpty()
        val adapter = RecentPreviewRVAdapter()
        adapter.submitList(recentList)
        binding.rvRecentPreview.adapter = adapter
        binding.tvAllRecent.setOnClickListener { onClickAllRecentlyViewed() }
    }
}