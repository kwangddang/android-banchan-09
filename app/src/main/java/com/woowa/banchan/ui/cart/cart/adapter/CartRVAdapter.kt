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

    private var checkHeaderViewHolder: CheckHeaderViewHolder? = null
    private var recentPreviewViewHolder: RecentPreviewViewHolder? = null
    private var totalPriceViewHolder: TotalPriceViewHolder? = null
    private var cartFooterBtnViewHolder: CartFooterBtnViewHolder? = null

    private var totalPrice = 0
    private var checkStateOriginCount = 0
    private var checkStateCount = 0
    private var listener: CartButtonCallBackListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CART_CHECK_HEADER -> {
                checkHeaderViewHolder = CheckHeaderViewHolder(
                    ItemCartCheckHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ),
                    onClickRemoveSelection = { onClickRemoveSelection() },
                    onClickAllSelection = { onClickAllSelection() },
                    onClickReleaseSelection = { onClickReleaseSelection() }
                )
                checkHeaderViewHolder!!
            }
            CART_CONTENT -> CartContentViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onClickCartRemove = { onClickCartRemove(it) },
                onClickCartCheckState = { onClickCartCheckState(it) },
                onClickCartUpdateCount = { cart, message -> onClickCartUpdateCount(cart, message) }
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
                    ),
                    onClickRecentItem = { onClickRecentItem(it) },
                    onClickAllRecentlyViewed = { onClickAllRecentlyViewed() },
                )
                recentPreviewViewHolder!!
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CART_CHECK_HEADER -> (holder as CheckHeaderViewHolder).bind(
                checkStateOriginCount,
                checkStateCount
            )
            CART_CONTENT -> (holder as CartContentViewHolder).bind(getItem(position))
            CART_TOTAL_PRICE -> (holder as TotalPriceViewHolder).bind(totalPrice)
            CART_FOOTER_BTN -> (holder as CartFooterBtnViewHolder).bind(totalPrice)
            else -> (holder as RecentPreviewViewHolder).bind(recentPreviewList)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            for (item in payloads)
                (holder as CartContentViewHolder).bind(cart = item as Cart)
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
        checkStateOriginCount = list.size
        checkStateCount = 0
        val newList = mutableListOf<Cart?>()

        newList.add(null)
        if (list.isEmpty()) newList.add(emptyCart())
        else list.forEachIndexed { index, cart ->
            if (cart.checkState) checkStateCount++
            newList.add(cart)
        }
        repeat(3) { newList.add(null) }

        submitList(newList)
        updateTotalPrice()
        checkHeaderViewHolder?.bind(checkStateOriginCount, checkStateCount)
    }

    private fun updateTotalPrice() {
        totalPrice = 0

        cartList.forEach { if (it.checkState) totalPrice += (it.price * it.count) }

        totalPriceViewHolder?.bind(totalPrice)
        cartFooterBtnViewHolder?.bind(totalPrice)
    }

    fun setCartButtonCallBackListener(listener: CartButtonCallBackListener) {
        this.listener = listener
    }

    private fun onClickRemoveSelection() {
        val removedList = cartList.filter {
            if (it.checkState) {
                listener?.onClickCartRemove(it)
                false
            } else true
        }
        submitCartList(removedList)
    }

    private fun onClickAllSelection() {
        val newList = cartList.map { cart ->
            cart.copy(checkState = true).apply { listener?.onClickCartUpdate(this) }
        }
        submitCartList(newList)
    }

    private fun onClickReleaseSelection() {
        val newList = cartList.map { cart ->
            cart.copy(checkState = false).apply { listener?.onClickCartUpdate(this) }
        }
        submitCartList(newList)
    }

    private fun onClickCartCheckState(cart: Cart) {
        checkStateCount = if (cart.checkState) checkStateCount + 1 else checkStateCount - 1
        checkHeaderViewHolder?.bind(checkStateOriginCount, checkStateCount)
        cartList.map { if (it.hash == cart.hash) it.checkState = cart.checkState }
        updateTotalPrice()
        listener?.onClickCartUpdate(cart)
    }

    private fun onClickCartUpdateCount(cart: Cart, message: Int? = null) {
        cartList.map { if (it.hash == cart.hash) it.count = cart.count }
        updateTotalPrice()
        listener?.onClickCartUpdate(cart, message)
    }

    private fun onClickCartRemove(cart: Cart) {
        val removedList = cartList.filter { cart.hash != it.hash }
        submitCartList(removedList)
        listener?.onClickCartRemove(cart)
    }

    private fun onClickOrderButton() {
        listener?.onClickOrderButton()
    }

    private fun onClickRecentItem(recent: Recent) {
        listener?.onClickRecentItem(recent)
    }

    private fun onClickAllRecentlyViewed() {
        listener?.onClickAllRecentlyViewed()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean =
                oldItem.hash == newItem.hash

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean =
                oldItem == newItem

            override fun getChangePayload(oldItem: Cart, newItem: Cart): Any? {
                if (oldItem.checkState != newItem.checkState)
                    return newItem
                return super.getChangePayload(oldItem, newItem)
            }
        }
    }

    interface CartButtonCallBackListener {

        fun onClickCartUpdate(cart: Cart, message: Int? = null)
        fun onClickCartRemove(hash: String)
        fun onClickOrderButton()
        fun onClickRecentItem(recent: Recent)
        fun onClickAllRecentlyViewed()
    }
}