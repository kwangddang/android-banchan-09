package com.woowa.banchan.ui.home.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.databinding.ItemBestRecyclerviewBinding
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemMainHeaderBinding
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.utils.HOME_HEADER
import com.woowa.banchan.utils.HOME_ITEM
import com.woowa.banchan.utils.SUB_HEADER

class MainAdapter : ListAdapter<Food, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> HomeHeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            SUB_HEADER -> MainHeaderViewHolder(
                ItemMainHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> HomeRecyclerViewViewHolder(
                ItemBestRecyclerviewBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HOME_HEADER -> (holder as HomeHeaderViewHolder).bind("한 번 주문하면\n두 번 반하는 반찬들", true)
            SUB_HEADER -> (holder as MainHeaderViewHolder)
            else -> (holder as HomeRecyclerViewViewHolder).bind(getItem(position).body)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HOME_HEADER
            1 -> SUB_HEADER
            else -> HOME_ITEM
        }
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.body == newItem.body
            }
        }
    }
}

class MainHeaderViewHolder(private val binding: ItemMainHeaderBinding) : RecyclerView.ViewHolder(binding.root) {}