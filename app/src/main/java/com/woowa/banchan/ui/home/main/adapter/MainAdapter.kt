package com.woowa.banchan.ui.home.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.Food
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemMainHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.utils.GRID
import com.woowa.banchan.utils.HOME_HEADER
import com.woowa.banchan.utils.HOME_ITEM
import com.woowa.banchan.utils.SUB_HEADER

class MainAdapter(private val checkedChangeListener: (RadioGroup, Int) -> Unit) : ListAdapter<Food, RecyclerView.ViewHolder>(diffUtil) {

    var managerType = GRID

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
                ItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HOME_HEADER -> (holder as HomeHeaderViewHolder).bind("모두가 좋아하는\n든든한 메인 요리", false)
            SUB_HEADER -> (holder as MainHeaderViewHolder).bind(checkedChangeListener)
            else -> (holder as HomeRecyclerViewViewHolder).bind(getItem(position).body, managerType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HOME_HEADER
            1 -> SUB_HEADER
            else -> HOME_ITEM
        }
    }

    fun submitHeaderList(food: Food) {
        val newList = mutableListOf<Food?>()
        newList.add(null)
        newList.add(null)
        newList.add(food)
        submitList(newList)
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

class MainHeaderViewHolder(private val binding: ItemMainHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(checkedChangeListener: (RadioGroup, Int) -> Unit) {
        binding.rgManager.setOnCheckedChangeListener { group, checkedId ->
            checkedChangeListener(group, checkedId)
        }
    }
}