package com.woowa.banchan.ui.home.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemMainHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.*
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.ui.home.main.adapter.viewholder.MainHeaderViewHolder

class MainRVAdapter(
    private val checkedChangeListener: (RadioGroup, Int) -> Unit,
    private val spinnerCallback: (Int) -> Unit,
    itemClickListener: (String, String) -> Unit,
    cartClickListener: (FoodItem) -> Unit
) :
    ListAdapter<RVItem, RecyclerView.ViewHolder>(diffUtil) {

    var managerType = GRID
    val homeRVAdapter: HomeRVAdapter = HomeRVAdapter(itemClickListener, cartClickListener).apply { managerType = GRID }

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
            SUB_HEADER -> (holder as MainHeaderViewHolder).bind(checkedChangeListener, spinnerCallback)
            else -> (holder as HomeRecyclerViewViewHolder).bind(homeRVAdapter, (getItem(position) as RVItem.Item<List<FoodItem>>).item, managerType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HOME_HEADER
            1 -> SUB_HEADER
            else -> HOME_ITEM
        }
    }

    fun submitHeaderList(food: List<FoodItem>) {
        val newList = mutableListOf<RVItem>()
        newList.add(RVItem.Header)
        newList.add(RVItem.SubHeader)
        newList.add(RVItem.Item(food))
        submitList(newList)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<RVItem>() {
            override fun areItemsTheSame(oldItem: RVItem, newItem: RVItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RVItem, newItem: RVItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}