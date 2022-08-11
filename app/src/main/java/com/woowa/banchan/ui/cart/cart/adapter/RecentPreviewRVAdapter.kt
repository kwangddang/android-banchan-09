package com.woowa.banchan.ui.cart.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemRecentPreviewBinding
import com.woowa.banchan.ui.cart.cart.adapter.viewholder.RecentPreviewItemViewHolder

class RecentPreviewRVAdapter : ListAdapter<FoodItem, RecentPreviewItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPreviewItemViewHolder {
        return RecentPreviewItemViewHolder(
            ItemRecentPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecentPreviewItemViewHolder, position: Int) {
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