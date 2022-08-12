package com.woowa.banchan.ui.home.best.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.BestFoodCategory
import com.woowa.banchan.databinding.ItemBestHeaderBinding

class BestHeaderViewHolder(private val binding: ItemBestHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(foodCategory: BestFoodCategory) {
        binding.beestFoodCategory = foodCategory
    }
}