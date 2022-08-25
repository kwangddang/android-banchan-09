package com.woowa.banchan.ui.home.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.databinding.ItemMainHeaderBinding
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.ui.common.recyclerview.RVItem
import com.woowa.banchan.ui.home.*
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter
import com.woowa.banchan.ui.home.adapter.viewholder.HomeHeaderViewHolder
import com.woowa.banchan.ui.home.adapter.viewholder.HomeRecyclerViewViewHolder
import com.woowa.banchan.ui.home.main.adapter.viewholder.MainHeaderViewHolder

class MainRVAdapter(
    private val checkedChangeListener: (RadioGroup, Int) -> Unit,
    private val spinnerPosition: Int,
    private val spinnerCallback: (Int) -> Unit,
    private val homeRVAdapter: HomeRVAdapter,
) :
    ListAdapter<RVItem, RecyclerView.ViewHolder>(diffUtil) {

    var managerType = GRID
    private var bannerTitle = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> {
                bannerTitle = parent.context.getString(R.string.home_header_main_title)

                HomeHeaderViewHolder(
                    ItemHomeHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
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
        when (holder) {
            is HomeHeaderViewHolder -> holder.bind(bannerTitle, false)
            is MainHeaderViewHolder -> holder.bind(
                spinnerPosition,
                checkedChangeListener,
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

    override fun getItemCount(): Int = 3

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