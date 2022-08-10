package com.woowa.banchan.ui.home.best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ItemBestBinding
import com.woowa.banchan.databinding.ItemBestHeaderBinding
import com.woowa.banchan.databinding.ItemHomeHeaderBinding

class BestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_HEADER -> HomeHeaderViewHolder(ItemHomeHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            BEST_HEADER -> BestHeaderViewHolder(ItemBestHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> BestViewHolder(ItemBestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 11

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
    }
}

class HomeHeaderViewHolder(private val binding: ItemHomeHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

}

class BestHeaderViewHolder(private val binding: ItemBestHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {

    }
}

class BestViewHolder(private val binding: ItemBestBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {

    }
}

