package com.woowa.banchan.ui.home.soup.adapter.viewholder

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemSoupSideHeaderBinding
import com.woowa.banchan.ui.home.main.adapter.SpinnerAdapter

class SoupSideHeaderViewHolder(private val binding: ItemSoupSideHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(count: Int, spinnerCallback: (Int) -> Unit) {
        val spinnerAdapter = SpinnerAdapter(binding.spinnerSort.context)
        binding.spinnerSort.adapter = spinnerAdapter
        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerAdapter.selectedPosition = position
                spinnerCallback(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.count = count
    }
}