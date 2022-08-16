package com.woowa.banchan.ui.home.best.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBestHeaderBinding
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.HOME_HEADER
import com.woowa.banchan.ui.home.HOME_ITEM
import com.woowa.banchan.ui.home.LINEAR_HORIZONTAL
import com.woowa.banchan.ui.home.SUB_HEADER
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.ui.home.best.adapter.viewholder.BestHeaderViewHolder

class BestRVAdapter(
    private val itemClickListener: (String, String) -> Unit,
    private val cartClickListener: (FoodItem) -> Unit
) : ListAdapter<BestFoodCategory, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> HomeHeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            SUB_HEADER -> BestHeaderViewHolder(
                ItemBestHeaderBinding.inflate(
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
            HOME_HEADER -> (holder as HomeHeaderViewHolder).bind("한 번 주문하면\n두 번 반하는 반찬들", true)
            SUB_HEADER -> (holder as BestHeaderViewHolder).bind(getItem(position))
            else -> (holder as HomeRecyclerViewViewHolder).bind(
                HomeRVAdapter(itemClickListener,cartClickListener).apply { managerType = LINEAR_HORIZONTAL },
                getItem(position).items,
                LINEAR_HORIZONTAL,
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            HOME_HEADER
        else {
            if (position % 2 == 1)
                SUB_HEADER
            else
                HOME_ITEM
        }
    }

    fun submitHeaderList(list: List<BestFoodCategory>) {
        val newList = mutableListOf<BestFoodCategory?>()
        newList.add(null)
        for (category in list) {
            newList.add(category)
            newList.add(category)
        }
        submitList(newList)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<BestFoodCategory>() {
            override fun areItemsTheSame(
                oldItem: BestFoodCategory,
                newItem: BestFoodCategory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: BestFoodCategory,
                newItem: BestFoodCategory
            ): Boolean {
                return oldItem.categoryId == newItem.categoryId
            }
        }
    }
}