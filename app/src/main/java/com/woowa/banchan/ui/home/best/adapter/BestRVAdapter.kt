package com.woowa.banchan.ui.home.best.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBestRecyclerviewBinding
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.HOME_HEADER
import com.woowa.banchan.ui.home.HOME_ITEM
import com.woowa.banchan.ui.home.RVItem
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.best.adapter.viewholder.BestViewHolder

class BestRVAdapter(
    private val itemClickListener: (String, String) -> Unit,
    private val cartClickListener: (FoodItem) -> Unit
) : ListAdapter<RVItem, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> HomeHeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> BestViewHolder(
                ItemBestRecyclerviewBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                BestItemAdapter(itemClickListener, cartClickListener)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeHeaderViewHolder -> holder.bind("한 번 주문하면\n두 번 반하는 반찬들", true)
            is BestViewHolder -> {
                holder.bind(
                    (getItem(position) as RVItem.Item<BestFoodCategory>).item.items,
                    (getItem(position) as RVItem.Item<BestFoodCategory>).item.name
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val item = payloads.getOrNull(0) as BestFoodCategory
            (holder as BestViewHolder).submitList(item.items)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            HOME_HEADER
        else
            HOME_ITEM
    }

    fun submitHeaderList(list: List<BestFoodCategory>) {
        val newList = mutableListOf<RVItem>()
        newList.add(RVItem.Header)
        for (category in list) {
            newList.add(RVItem.Item(category))
        }
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

            override fun getChangePayload(oldItem: RVItem, newItem: RVItem): Any? {
                return (newItem as RVItem.Item<BestFoodCategory>).item
            }
        }
    }
}