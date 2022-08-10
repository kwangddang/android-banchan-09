package com.woowa.banchan.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemHomeBinding

class HomeItemAdapter(private val categoryFood: List<FoodItem>) :
    RecyclerView.Adapter<BestItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemViewHolder {
        return BestItemViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BestItemViewHolder, position: Int) {
        holder.bind(categoryFood[position])
    }

    override fun getItemCount(): Int = categoryFood.size
}

class BestItemViewHolder(private val binding: ItemHomeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(food: FoodItem) {
        binding.food = food
    }
}
