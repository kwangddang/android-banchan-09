package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.BestFoodCategory
import com.woowa.banchan.databinding.ItemCartBinding

class CartContentViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(foodCategory: BestFoodCategory) {
        /*binding.beestFoodCategory = foodCategory*/
    }
}