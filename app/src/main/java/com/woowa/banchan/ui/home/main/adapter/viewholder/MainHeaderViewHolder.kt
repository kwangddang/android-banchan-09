package com.woowa.banchan.ui.home.main.adapter.viewholder

import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemMainHeaderBinding
import com.woowa.banchan.ui.home.main.adapter.SpinnerAdapter

class MainHeaderViewHolder(private val binding: ItemMainHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(checkedChangeListener: (RadioGroup, Int) -> Unit) {
        binding.spinnerSort.adapter = SpinnerAdapter(binding.spinnerSort.context)
        binding.rgManager.setOnCheckedChangeListener { group, checkedId ->
            checkedChangeListener(group, checkedId)
        }
    }
}