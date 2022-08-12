package com.woowa.banchan.ui.home.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.FoodItemDto
import com.woowa.banchan.databinding.ItemHomeBinding
import com.woowa.banchan.databinding.ItemMainLinearBinding
import com.woowa.banchan.domain.model.FoodItem

class HomeItemViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(food: FoodItem) {
        if (binding is ItemHomeBinding)
            binding.food = food
        else if (binding is ItemMainLinearBinding)
            binding.food = food
    }
}