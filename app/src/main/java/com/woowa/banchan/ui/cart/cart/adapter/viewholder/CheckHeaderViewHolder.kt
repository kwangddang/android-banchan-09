package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemCartCheckHeaderBinding

class CheckHeaderViewHolder(private val binding: ItemCartCheckHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var onClickRemoveSelection: () -> Unit = {}
    var onClickReleaseSelection: () -> Unit = {}

    fun bind() {
        binding.layoutUncheckBtn.setOnClickListener { onClickRemoveSelection() }
        binding.tvReleaseChecked.setOnClickListener { onClickReleaseSelection() }
    }
}