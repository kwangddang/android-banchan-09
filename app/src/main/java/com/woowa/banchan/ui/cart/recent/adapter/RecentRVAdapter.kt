package com.woowa.banchan.ui.cart.recent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.databinding.ItemRecentGridBinding
import com.woowa.banchan.domain.model.Recent

class RecentRVAdapter : ListAdapter<Recent, RecentGridItemViewHolder>(diffUtil) {

    private var listener: RecentlyViewedCallBackListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentGridItemViewHolder {
        return RecentGridItemViewHolder(
            ItemRecentGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecentGridItemViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            onClickItem = { onClickItem(it) },
            onClickCartButton = { onClickCartButton(it) },
            onClickCheckButton = { onClickCheckButton(it) }
        )
    }

    override fun onBindViewHolder(holder: RecentGridItemViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.bind(payloads.first() as Boolean)
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

            override fun getChangePayload(oldItem: Recent, newItem: Recent): Any? {
                if (oldItem.checkState != newItem.checkState)
                    return newItem.checkState

                return super.getChangePayload(oldItem, newItem)
            }
        }
    }

    interface RecentlyViewedCallBackListener {

        fun onClickCartButton(recent: Recent)
        fun onClickCheckButton(recent: Recent)
        fun onClickItem(recent: Recent)
    }
}