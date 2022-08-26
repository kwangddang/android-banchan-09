package com.woowa.banchan.ui.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemVpDetailBinding

class DetailVPViewHolder(private val binding: ItemVpDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(url: String) {
        binding.url = url
    }
}