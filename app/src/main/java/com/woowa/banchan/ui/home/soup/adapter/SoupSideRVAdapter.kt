package com.woowa.banchan.ui.home.soup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.databinding.ItemSoupSideHeaderBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.GRID
import com.woowa.banchan.ui.home.HOME_HEADER
import com.woowa.banchan.ui.home.HOME_ITEM
import com.woowa.banchan.ui.home.SUB_HEADER
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.ui.home.soup.adapter.viewholder.SoupSideHeaderViewHolder

class SoupSideRVAdapter(
    private val spinnerCallback: (Int) -> Unit,
    itemClickListener: (String, String) -> Unit,
    cartClickListener: (FoodItem) -> Unit
) :
    ListAdapter<List<FoodItem>, RecyclerView.ViewHolder>(diffUtil) {

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
            HOME_HEADER -> (holder as HomeHeaderViewHolder).bind("정성이 담긴\n뜨끈뜨끈 국물 요리", false)
            SUB_HEADER -> (holder as SoupSideHeaderViewHolder).bind(getItem(2).size, spinnerCallback)
            else -> (holder as HomeRecyclerViewViewHolder).bind(homeRVAdapter, getItem(position), managerType)
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
        val newList = mutableListOf<List<FoodItem>?>()
        newList.add(null)
        newList.add(null)
        newList.add(food)
        submitList(newList)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<List<FoodItem>>() {
            override fun areItemsTheSame(oldItem: List<FoodItem>, newItem: List<FoodItem>): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: List<FoodItem>, newItem: List<FoodItem>): Boolean {
                return oldItem == newItem
            }
        }
    }
}