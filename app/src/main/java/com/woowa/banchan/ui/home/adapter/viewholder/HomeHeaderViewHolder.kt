package com.woowa.banchan.ui.home.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemHomeHeaderBinding

class HomeHeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String, isBadge: Boolean) {
        binding.title = text
        if (isBadge) {
            binding.tvBadge.visibility = View.VISIBLE
        } else {
            binding.tvBadge.visibility = View.GONE
        }
    }
}