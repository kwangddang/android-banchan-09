package com.woowa.banchan.ui.cart.cart.adapter

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
    private var cartList = mutableListOf<Cart>()

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
                ),
                onClickRemoveSelection = { onClickRemoveSelection() },
                onClickReleaseSelection = { onClickReleaseSelection() }
            )
            CART_CONTENT -> CartContentViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onClickCartRemove = { onClickCartRemove(it) },
                onClickCartCheckState = { onClickCartCheckState(it) },
                onClickCartUpdateCount = { i, m -> onClickCartUpdateCount(i, m) }
            )
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
                    ),
                    onClickOrderButton = { onClickOrderButton() }
                )
                cartFooterBtnViewHolder!!
            }
            else -> {
                recentPreviewViewHolder = RecentPreviewViewHolder(
                    ItemRecentPreviewBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ), onClickAllRecentlyViewed
                    = { onClickAllRecentlyViewed() }
                )
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
        cartList = list.toMutableList()
        val newList = mutableListOf<Cart?>()

        newList.add(null)
        if (list.isEmpty()) newList.add(emptyCart())
        else list.forEach { newList.add(it) }
        repeat(3) { newList.add(null) }

        submitList(newList)
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        totalPrice = 0

        cartList.forEach {
            if (it.checkState) totalPrice += (it.price * it.count)
        }
        totalPriceViewHolder?.bind(totalPrice)
        cartFooterBtnViewHolder?.bind(totalPrice)
    }

    fun setCartButtonCallBackListener(listener: CartButtonCallBackListener) {
        this.listener = listener
    }

    private fun onClickRemoveSelection() {
        val tmpList = cartList.toMutableList()
        tmpList.forEach { if (it.checkState) onClickCartRemove(it) }
    }

    private fun onClickReleaseSelection() {
        cartList.forEach {
            if (it.checkState) {
                it.checkState = false
                onClickCartCheckState(it)
            }
        }
        notifyDataSetChanged()
    }

    private fun onClickCartCheckState(cart: Cart) {
        updateTotalPrice()
        listener?.onClickCartUpdate(cart)
    }

    private fun onClickCartUpdateCount(cart: Cart, message: String? = null) {
        updateTotalPrice()
        listener?.onClickCartUpdate(cart, message)
    }

    private fun onClickCartRemove(cart: Cart) {
        cartList.remove(cart)
        submitCartList(cartList)
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

        fun onClickCartUpdate(cart: Cart, message: String? = null)
        fun onClickCartRemove(cart: Cart)
        fun onClickOrderButton()
        fun onClickAllRecentlyViewed()
    }
}