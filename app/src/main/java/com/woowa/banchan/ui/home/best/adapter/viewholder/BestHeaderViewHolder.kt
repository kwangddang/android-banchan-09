package com.woowa.banchan.ui.home.best.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.BestFoodCategoryDto
import com.woowa.banchan.databinding.ItemBestHeaderBinding

class BestHeaderViewHolder(private val binding: ItemBestHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(foodCategory: BestFoodCategoryDto) {
        binding.beestFoodCategory = foodCategory
    }
}