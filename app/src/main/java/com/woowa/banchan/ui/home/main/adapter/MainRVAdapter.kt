package com.woowa.banchan.ui.home.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.FoodDto
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemMainHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.ui.home.GRID
import com.woowa.banchan.ui.home.HOME_HEADER
import com.woowa.banchan.ui.home.HOME_ITEM
import com.woowa.banchan.ui.home.SUB_HEADER
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.ui.home.main.adapter.viewholder.MainHeaderViewHolder

class MainRVAdapter(private val checkedChangeListener: (RadioGroup, Int) -> Unit) :
    ListAdapter<FoodDto, RecyclerView.ViewHolder>(diffUtil) {

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

    fun submitHeaderList(food: FoodDto) {
        val newList = mutableListOf<FoodDto?>()
        newList.add(null)
        newList.add(null)
        newList.add(food)
        submitList(newList)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<FoodDto>() {
            override fun areItemsTheSame(oldItem: FoodDto, newItem: FoodDto): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FoodDto, newItem: FoodDto): Boolean {
                return oldItem.body == newItem.body
            }
        }
    }
}