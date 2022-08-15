package com.woowa.banchan.ui.home.adapter.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemRecyclerviewBinding
import com.woowa.banchan.ui.home.LINEAR_HORIZONTAL
import com.woowa.banchan.ui.home.LINEAR_VERTICAL
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter

class HomeRecyclerViewViewHolder(private val binding: ItemRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(food: List<FoodItem>, managerType: Int, itemClickListener: (String, String) -> Unit) {
        val adapter = HomeRVAdapter(managerType, itemClickListener)
        binding.rvBest.apply {
            layoutManager = when (managerType) {
                LINEAR_HORIZONTAL -> LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                LINEAR_VERTICAL -> LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                else -> GridLayoutManager(this.context, 2)
            }
        }
        adapter.submitList(food)
        binding.rvBest.adapter = adapter
    }
}