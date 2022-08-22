package com.woowa.banchan.ui.home.best.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBestRecyclerviewBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.best.adapter.BestItemAdapter

class BestViewHolder(private val binding: ItemBestRecyclerviewBinding, private val bestItemAdapter: BestItemAdapter) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(food: List<FoodItem>, categoryName: String) {
        binding.rvBest.adapter = bestItemAdapter
        binding.categoryName = categoryName
        bestItemAdapter.submitList(food)
    }

    fun submitList(foods: List<FoodItem>) {
        bestItemAdapter.submitList(foods)
    }
}