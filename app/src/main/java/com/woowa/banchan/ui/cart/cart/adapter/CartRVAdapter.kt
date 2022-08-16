package com.woowa.banchan.ui.cart.cart.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.*
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.model.emptyCart
import com.woowa.banchan.ui.cart.*
import com.woowa.banchan.ui.cart.cart.adapter.viewholder.*

class CartRVAdapter : ListAdapter<Cart, RecyclerView.ViewHolder>(diffUtil) {

    private var recentPreviewList = listOf<Recent>()
    private var recentPreviewViewHolder: RecentPreviewViewHolder? = null
    private var totalPriceViewHolder: TotalPriceViewHolder? = null
    private var cartFooterBtnViewHolder: CartFooterBtnViewHolder? = null

    private var totalPrice = 0
    private var listener: CartButtonCallBackListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CART_CHECK_HEADER -> CheckHeaderViewHolder(
                ItemCartCheckHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            ).apply {
                onClickRemoveSelection = this@CartRVAdapter::onClickRemoveSelection
                onClickReleaseSelection = this@CartRVAdapter::onClickReleaseSelection
            }
            CART_CONTENT -> CartContentViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            ).apply {
                onClickCartRemove = this@CartRVAdapter::onClickCartRemove
                onClickCartCheckState = this@CartRVAdapter::onClickCartCheckState
                onClickCartUpdateCount = this@CartRVAdapter::onClickCartUpdateCount
            }
            CART_TOTAL_PRICE -> {
                totalPriceViewHolder = TotalPriceViewHolder(
                    ItemTotalPriceBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
                totalPriceViewHolder!!
            }
            CART_FOOTER_BTN -> {
                cartFooterBtnViewHolder = CartFooterBtnViewHolder(
                    ItemCartButtonFooterBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                ).apply {
                    onClickOrderButton = this@CartRVAdapter::onClickOrderButton
                }
                cartFooterBtnViewHolder!!
            }
            else -> {
                recentPreviewViewHolder = RecentPreviewViewHolder(
                    ItemRecentPreviewBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ),
                ).apply {
                    onClickAllRecentlyViewed = this@CartRVAdapter::onClickAllRecentlyViewed
                }
                recentPreviewViewHolder!!
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CART_CHECK_HEADER -> (holder as CheckHeaderViewHolder).bind()
            CART_CONTENT -> (holder as CartContentViewHolder).bind(getItem(position))
            CART_TOTAL_PRICE -> (holder as TotalPriceViewHolder).bind(totalPrice)
            CART_FOOTER_BTN -> (holder as CartFooterBtnViewHolder).bind(totalPrice)
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

    fun setPreviewList(recentItems: List<Recent>) {
        this.recentPreviewList = recentItems
        recentPreviewViewHolder?.bind(recentPreviewList)
    }

    fun submitCartList(list: List<Cart>) {
        // 첫번째, 마지막, 마지막-1,마지막-2
        val newList = mutableListOf<Cart?>()

        totalPrice = 0

        newList.add(null)
        if (list.isEmpty()) newList.add(emptyCart())
        else list.forEach {
            newList.add(it)
            if (it.checkState) totalPrice += it.price
            Log.d("Tester", "submitCartList: $it")
        }
        repeat(3) { newList.add(null) }

        submitList(newList)
        totalPriceViewHolder?.bind(totalPrice)
        cartFooterBtnViewHolder?.bind(totalPrice)
    }

    fun setCartButtonCallBackListener(listener: CartButtonCallBackListener) {
        this.listener = listener
    }

    private fun onClickRemoveSelection() {
        listener?.onClickRemoveSelection()
    }

    private fun onClickReleaseSelection() {
        listener?.onClickReleaseSelection()
    }

    private fun onClickCartCheckState(cart: Cart) {
        listener?.onClickCartCheckState(cart)
    }

    private fun onClickCartUpdateCount(cart: Cart, count: Int) {
        listener?.onClickCartUpdateCount(cart, count)
    }

    private fun onClickCartRemove(cart: Cart) {
        listener?.onClickCartRemove(cart)
    }

    private fun onClickOrderButton() {
        listener?.onClickOrderButton()
    }

    private fun onClickAllRecentlyViewed() {
        listener?.onClickAllRecentlyViewed()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean =
                oldItem.hash == newItem.hash
        }
    }

    interface CartButtonCallBackListener {

        fun onClickRemoveSelection()
        fun onClickReleaseSelection()
        fun onClickCartCheckState(cart: Cart)
        fun onClickCartUpdateCount(cart: Cart, count: Int)
        fun onClickCartRemove(cart: Cart)
        fun onClickOrderButton()
        fun onClickAllRecentlyViewed()
    }
}