package com.woowa.banchan.ui.home.best.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBestBinding
import com.woowa.banchan.domain.model.FoodItem

class BestItemViewHolder(private val binding: ItemBestBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        food: FoodItem,
        itemClickListener: (String, String) -> Unit,
        cartClickListener: (FoodItem) -> Unit
    ) {
        binding.food = food
        binding.checkState = food.checkState
        binding.layoutHome.setOnClickListener {
            itemClickListener(food.title, food.detailHash)
        }
        binding.ivCart.setOnClickListener {
            cartClickListener(food)
        }
        binding.ivCheck.setOnClickListener {
            cartClickListener(food)
        }
    }

    fun bind(checkState: Boolean) {
        binding.food!!.checkState = checkState
        binding.checkState = checkState
    }
}