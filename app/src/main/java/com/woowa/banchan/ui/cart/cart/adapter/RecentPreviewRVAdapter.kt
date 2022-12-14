package com.woowa.banchan.ui.cart.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.woowa.banchan.databinding.ItemRecentBinding
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.cart.adapter.viewholder.RecentItemViewHolder

class RecentPreviewRVAdapter(
    private val onClickItem: (recent: Recent) -> Unit,
) : ListAdapter<Recent, RecentItemViewHolder>(diffUtil) {

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
            onClickItem = { onClickItem(it) },
        )
    }


    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Recent>() {
            override fun areItemsTheSame(oldItem: Recent, newItem: Recent): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Recent, newItem: Recent): Boolean {
                return oldItem.hash == newItem.hash
            }
        }
    }
}