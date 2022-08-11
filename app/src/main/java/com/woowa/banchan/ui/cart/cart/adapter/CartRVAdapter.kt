package com.woowa.banchan.ui.cart.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.BestFoodCategory
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.*
import com.woowa.banchan.ui.cart.cart.adapter.viewholder.*

class CartRVAdapter : ListAdapter<BestFoodCategory, RecyclerView.ViewHolder>(diffUtil) {

    private var recentPreviewList = listOf<FoodItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CART_CHECK_HEADER -> CheckHeaderViewHolder(
                ItemCartCheckHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            CART_CONTENT -> CartContentViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            CART_TOTAL_PRICE -> TotalPriceViewHolder(
                ItemTotalPriceBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            CART_FOOTER_BTN -> CartFooterBtnViewHolder(
                ItemCartButtonFooterBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> RecentPreviewItemViewHolder(
                ItemRecentPreviewBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CART_CHECK_HEADER -> (holder as CheckHeaderViewHolder).bind()
            CART_CONTENT -> (holder as CartContentViewHolder).bind(getItem(position))
            CART_TOTAL_PRICE -> (holder as TotalPriceViewHolder).bind(getItem(position))
            CART_FOOTER_BTN -> (holder as CartFooterBtnViewHolder).bind(getItem(position))
            else -> (holder as RecentPreviewViewHolder).bind(recentPreviewList)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val lastIdx = itemCount - 1

        return when (position) {
            0 -> CART_CHECK_HEADER
            lastIdx - 2 -> CART_TOTAL_PRICE
            lastIdx - 1 -> CART_FOOTER_BTN
            lastIdx -> RECENT_PREVIEW
            else -> CART_CONTENT
        }
    }

    fun setPreviewList(recentItems: List<FoodItem>) {
        this.recentPreviewList = recentItems
    }

    fun submitHeaderList(list: List<BestFoodCategory>) {
        submitList(list)
    }

    companion object {
        const val CART_CHECK_HEADER = 0
        const val CART_CONTENT = 1
        const val CART_TOTAL_PRICE = 2
        const val CART_FOOTER_BTN = 3
        const val RECENT_PREVIEW = 4

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