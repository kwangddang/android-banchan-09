package com.woowa.banchan.ui.home.best.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBestRecyclerviewBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.best.adapter.BestItemAdapter

class BestViewHolder(private val binding: ItemBestRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(bestItemAdapter: BestItemAdapter, food: List<FoodItem>) {
        binding.rvBest.adapter = bestItemAdapter
        bestItemAdapter.submitList(food)

    }
}