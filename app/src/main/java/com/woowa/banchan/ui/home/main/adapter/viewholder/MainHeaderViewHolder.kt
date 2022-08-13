package com.woowa.banchan.ui.home.main.adapter.viewholder

import android.view.View
import android.widget.AdapterView
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemMainHeaderBinding
import com.woowa.banchan.ui.home.main.adapter.SpinnerAdapter

class MainHeaderViewHolder(private val binding: ItemMainHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(checkedChangeListener: (RadioGroup, Int) -> Unit, spinnerCallback: (Int) -> Unit) {
        val spinnerAdapter = SpinnerAdapter(binding.spinnerSort.context)
        binding.spinnerSort.adapter = spinnerAdapter
        binding.spinnerSort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerAdapter.selectedPosition = position
                spinnerCallback(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.rgManager.setOnCheckedChangeListener { group, checkedId ->
            checkedChangeListener(group, checkedId)
        }
    }
}