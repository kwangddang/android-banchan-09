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
import com.woowa.banchan.ui.home.*
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.ui.home.best.adapter.viewholder.BestHeaderViewHolder

class BestRVAdapter(
    private val itemClickListener: (String, String) -> Unit,
    private val cartClickListener: (FoodItem) -> Unit
) : ListAdapter<RecyclerViewItem, RecyclerView.ViewHolder>(diffUtil) {

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
            SUB_HEADER -> (holder as BestHeaderViewHolder).bind((getItem(position) as RecyclerViewItem.Item<BestFoodCategory>).item)
            else -> (holder as HomeRecyclerViewViewHolder).bind(
                HomeRVAdapter(itemClickListener, cartClickListener).apply { managerType = LINEAR_HORIZONTAL },
                (getItem(position) as RecyclerViewItem.Item<BestFoodCategory>).item.items,
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
        val newList = mutableListOf<RecyclerViewItem>()
        newList.add(RecyclerViewItem.Header)
        for (category in list) {
            newList.add(RecyclerViewItem.Item(category))
            newList.add(RecyclerViewItem.Item(category))
        }
        submitList(newList)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<RecyclerViewItem>() {
            override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}