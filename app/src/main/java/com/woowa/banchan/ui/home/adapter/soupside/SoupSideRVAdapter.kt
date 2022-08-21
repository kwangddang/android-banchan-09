package com.woowa.banchan.ui.home.adapter.soupside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.databinding.ItemSoupSideHeaderBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.*
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter
import com.woowa.banchan.ui.home.adapter.soupside.viewholder.SoupSideHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder

class SoupSideRVAdapter(
    private val isSoup: Boolean,
    private val spinnerCallback: (Int) -> Unit,
    itemClickListener: (String, String) -> Unit,
    cartClickListener: (FoodItem) -> Unit
) :
    ListAdapter<RVItem, RecyclerView.ViewHolder>(diffUtil) {

    var managerType = GRID
    private val homeRVAdapter: HomeRVAdapter = HomeRVAdapter(itemClickListener, cartClickListener).apply { managerType = GRID }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> HomeHeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            SUB_HEADER -> SoupSideHeaderViewHolder(
                ItemSoupSideHeaderBinding.inflate(
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
            HOME_HEADER -> (holder as HomeHeaderViewHolder).bind(
                if (isSoup)
                    "정성이 담긴\n뜨끈뜨끈 국물 요리"
                else
                    "식탁을 풍성하게 하는\n정갈한 밑반찬", false
            )
            SUB_HEADER -> (holder as SoupSideHeaderViewHolder).bind((getItem(2) as RVItem.Item<List<FoodItem>>).item.size, spinnerCallback)
            else -> (holder as HomeRecyclerViewViewHolder).bind(homeRVAdapter, (getItem(2) as RVItem.Item<List<FoodItem>>).item, managerType)
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