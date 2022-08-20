package com.woowa.banchan.ui.home.best.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.databinding.ItemBestBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.best.adapter.viewholder.BestItemViewHolder

class BestItemAdapter(
    private val itemClickListener: (String, String) -> Unit,
    private val cartClickListener: (FoodItem) -> Unit
) :
    ListAdapter<FoodItem, BestItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemViewHolder {
        return BestItemViewHolder(
            ItemBestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BestItemViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener, cartClickListener)
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


