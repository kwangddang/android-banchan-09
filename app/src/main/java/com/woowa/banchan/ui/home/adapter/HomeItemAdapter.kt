package com.woowa.banchan.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemHomeBinding
import com.woowa.banchan.databinding.ItemMainBinding
import com.woowa.banchan.ui.home.adapter.viewholder.HomeItemViewHolder
import com.woowa.banchan.utils.LINEAR_VERTICAL

class HomeItemAdapter(private val managerType: Int) : ListAdapter<FoodItem, HomeItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        return if (managerType == LINEAR_VERTICAL) {
            HomeItemViewHolder(
                ItemMainBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            HomeItemViewHolder(
                ItemHomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
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


