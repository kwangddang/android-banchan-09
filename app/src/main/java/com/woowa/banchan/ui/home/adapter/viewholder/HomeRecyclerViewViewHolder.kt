package com.woowa.banchan.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemBestRecyclerviewBinding
import com.woowa.banchan.ui.home.adapter.HomeItemAdapter

class HomeRecyclerViewViewHolder(private val binding: ItemBestRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(food: List<FoodItem>) {
        val adapter = HomeItemAdapter()
        adapter.submitList(food)
        binding.layoutBest.adapter = adapter
    }
}