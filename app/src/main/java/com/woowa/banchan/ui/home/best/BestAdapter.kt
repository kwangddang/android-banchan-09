package com.woowa.banchan.ui.home.best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.data.remote.dto.BestFoodCategory
import com.woowa.banchan.data.remote.dto.FoodItem
import com.woowa.banchan.databinding.ItemBestHeaderBinding
import com.woowa.banchan.databinding.ItemBestRecyclerviewBinding
import com.woowa.banchan.databinding.ItemHomeHeaderBinding
import com.woowa.banchan.ui.home.HomeItemAdapter

class BestAdapter : ListAdapter<BestFoodCategory,RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> HomeHeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            BEST_HEADER -> BestHeaderViewHolder(
                ItemBestHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> BestRecyclerViewViewHolder(
                ItemBestRecyclerviewBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HOME_HEADER -> (holder as HomeHeaderViewHolder).bind()
            BEST_HEADER -> (holder as BestHeaderViewHolder).bind(getItem(position / 2))
            else -> (holder as BestRecyclerViewViewHolder).bind(getItem(position / 2 - 1).items)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size * 2 + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            HOME_HEADER
        else {
            if (position % 2 == 1)
                BEST_HEADER
            else
                BEST
        }
    }

    companion object {
        const val HOME_HEADER = 1
        const val BEST_HEADER = 2
        const val BEST = 3

        val diffUtil = object : DiffUtil.ItemCallback<BestFoodCategory>() {
            override fun areItemsTheSame(oldItem: BestFoodCategory, newItem: BestFoodCategory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BestFoodCategory, newItem: BestFoodCategory): Boolean {
                return oldItem.categoryId == newItem.categoryId
            }
        }
    }
}

class HomeHeaderViewHolder(private val binding: ItemHomeHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.tvTitle.apply { text = this.context.getString(R.string.best_title) }
    }
}

class BestHeaderViewHolder(private val binding: ItemBestHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(foodCategory: BestFoodCategory) {
        binding.beestFoodCategory = foodCategory
    }
}

class BestRecyclerViewViewHolder(private val binding: ItemBestRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(categoryFood: List<FoodItem>) {
        val adapter = HomeItemAdapter()
        adapter.submitList(categoryFood)
        binding.layoutBest.adapter = adapter
    }
}