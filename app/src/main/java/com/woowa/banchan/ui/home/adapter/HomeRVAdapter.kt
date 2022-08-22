package com.woowa.banchan.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.databinding.ItemHomeBinding
import com.woowa.banchan.databinding.ItemMainLinearBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.LINEAR_VERTICAL
import com.woowa.banchan.ui.home.adapter.viewholder.HomeItemViewHolder

class HomeRVAdapter(
    private val itemClickListener: (String, String) -> Unit,
    private val cartClickListener: (FoodItem) -> Unit
) :
    ListAdapter<FoodItem, HomeItemViewHolder>(diffUtil) {

    var managerType: Int = LINEAR_VERTICAL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        return if (managerType == LINEAR_VERTICAL) {
            HomeItemViewHolder(
                ItemMainLinearBinding.inflate(
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
        holder.bind(getItem(position), itemClickListener, cartClickListener)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind(getItem(position))
        }
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<FoodItem>() {
            override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
                return oldItem.detailHash == newItem.detailHash
            }

            override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: FoodItem, newItem: FoodItem): Any? {
                if(oldItem.checkState != newItem.checkState) {
                    return true
                }
                return super.getChangePayload(oldItem, newItem)
            }
        }
    }
}


