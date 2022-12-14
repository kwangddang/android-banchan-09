package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemCartCheckHeaderBinding

class CheckHeaderViewHolder(
    private val binding: ItemCartCheckHeaderBinding,
    private val onClickRemoveSelection: () -> Unit,
    private val onClickAllSelection: () -> Unit,
    private val onClickReleaseSelection: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(checkStateOriginCount: Int, checkStateCount: Int) {
        binding.checkStateFlag = checkStateCount == checkStateOriginCount
        binding.tvRemoveSelection.setOnClickListener { onClickRemoveSelection() }
        binding.tvAllChecked.setOnClickListener { onClickAllSelection() }
        binding.tvReleaseChecked.setOnClickListener { onClickReleaseSelection() }
    }
}