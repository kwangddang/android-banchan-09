package com.woowa.banchan.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemHomeBinding

class HomeItemAdapter : ListAdapter<FoodItem, BestItemViewHolder>(diffUtil) {

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
        holder.bind(getItem(position))
    }


    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<FoodItem>() {
            override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }
        }
    }
}

class BestItemViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(food: FoodItem) {
        binding.food = food
    }
}
