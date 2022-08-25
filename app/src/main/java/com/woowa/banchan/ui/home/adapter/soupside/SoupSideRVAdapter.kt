package com.woowa.banchan.ui.home.adapter.soupside

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.databinding.ItemSoupSideHeaderBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.HOME_HEADER
import com.woowa.banchan.ui.home.HOME_ITEM
import com.woowa.banchan.ui.home.RVItem
import com.woowa.banchan.ui.home.SUB_HEADER
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter
import com.woowa.banchan.ui.home.adapter.soupside.viewholder.SoupSideHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder

class SoupSideRVAdapter(
    private val isSoup: Boolean,
    private val spinnerPosition: Int,
    private val spinnerCallback: (Int) -> Unit,
    private val homeRVAdapter: HomeRVAdapter
) :
    ListAdapter<RVItem, RecyclerView.ViewHolder>(diffUtil) {

    private var bannerTitle = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> {
                with(parent.context) {
                    bannerTitle =
                        if (isSoup) getString(R.string.home_header_soup_title)
                        else getString(R.string.home_header_side_title)
                }
                HomeHeaderViewHolder(
                    ItemHomeHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
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
        when (holder) {
            is HomeHeaderViewHolder -> holder.bind(bannerTitle, false)
            is SoupSideHeaderViewHolder -> holder.bind(
                homeRVAdapter.itemCount,
                spinnerPosition,
                spinnerCallback
            )
            is HomeRecyclerViewViewHolder -> holder.bind(homeRVAdapter)
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