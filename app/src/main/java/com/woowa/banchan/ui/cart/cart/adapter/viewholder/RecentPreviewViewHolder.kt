package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRecentPreviewBinding
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.cart.adapter.RecentPreviewRVAdapter

class RecentPreviewViewHolder(
    private val binding: ItemRecentPreviewBinding,
    private val onClickRecentItem: (recent: Recent) -> Unit,
    private val onClickAllRecentlyViewed: () -> Unit = {}
) :
    RecyclerView.ViewHolder(binding.root) {

    val maxCount = 7

    fun bind(recentList: List<Recent>) {
        binding.tvEmptyNotice.isVisible = recentList.isEmpty()
        val list = mutableListOf<Recent>()
        recentList.forEachIndexed { index, recent -> if (index < maxCount) list.add(recent) }
        val adapter = RecentPreviewRVAdapter(onClickRecentItem)
        adapter.submitList(list)
        binding.rvRecentPreview.adapter = adapter
        binding.tvAllRecent.setOnClickListener { onClickAllRecentlyViewed() }
    }
}