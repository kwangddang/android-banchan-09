package com.woowa.banchan.ui.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRvDetailBinding

class DetailRVViewHolder(private val binding: ItemRvDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(url: String) {
        binding.url = url
    }
}