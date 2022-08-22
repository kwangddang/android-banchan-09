package com.woowa.banchan.ui.cart.recent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.databinding.ItemRecentBinding
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.cart.adapter.viewholder.RecentItemViewHolder

class RecentRVAdapter : ListAdapter<Recent, RecentItemViewHolder>(diffUtil) {

    private var listener: RecentlyViewedCallBackListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentItemViewHolder {
        return RecentItemViewHolder(
            ItemRecentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecentItemViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            isPreview = false,
            onClickItem = { onClickItem(it) },
            onClickCartButton = { onClickCartButton(it) },
            onClickCheckButton = { onClickCheckButton(it) }
        )
    }

    fun setCartList(cartItems: List<Cart>) {
        val list = currentList.toMutableList()
        list.forEachIndexed { index, it ->
            var isInCart = false
            for (item in cartItems) {
                if (item.hash == it.hash) {
                    isInCart = true
                    if (it.checkState.not()) {
                        it.checkState = true
                        notifyItemChanged(index)
                        break
                    }
                }
            }
            if (!isInCart && it.checkState) {
                it.checkState = false
                notifyItemChanged(index)
            }
        }
    }

    fun setPreviewList(recentItems: List<Recent>) {
        submitList(recentItems)
    }

    private fun onClickItem(recent: Recent) {
        listener?.onClickItem(recent)
    }

    private fun onClickCheckButton(recent: Recent) {
        listener?.onClickCheckButton(recent)
    }

    private fun onClickCartButton(recent: Recent) {
        listener?.onClickCartButton(recent)
    }

    fun setRecentlyViewedCallBackListener(listener: RecentlyViewedCallBackListener) {
        this.listener = listener
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Recent>() {
            override fun areItemsTheSame(oldItem: Recent, newItem: Recent): Boolean {
                return oldItem.hash == newItem.hash
            }

            override fun areContentsTheSame(oldItem: Recent, newItem: Recent): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface RecentlyViewedCallBackListener {

        fun onClickCartButton(recent: Recent)
        fun onClickCheckButton(recent: Recent)
        fun onClickItem(recent: Recent)
    }
}