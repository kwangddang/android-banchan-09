package com.woowa.banchan.ui.home.best.adapter.viewholder

import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemBestRecyclerviewBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.home.best.adapter.BestItemAdapter
import kotlin.math.abs

class BestViewHolder(private val binding: ItemBestRecyclerviewBinding, private val bestItemAdapter: BestItemAdapter) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(food: List<FoodItem>, categoryName: String) {
        binding.rvBest.adapter = bestItemAdapter
        binding.rvBest.addOnItemTouchListener(getOnItemTouchListener())
        binding.categoryName = categoryName
        bestItemAdapter.submitList(food)
    }

    fun submitList(foods: List<FoodItem>) {
        bestItemAdapter.submitList(foods)
    }

    private fun getOnItemTouchListener(): RecyclerView.OnItemTouchListener {
        var lastX = 0
        var lastY = 0
        return object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = e.x.toInt()
                        lastY = e.y.toInt()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val distanceX = abs(lastX - e.x)
                        val distanceY = abs(lastY - e.y)
                        rv.parent.requestDisallowInterceptTouchEvent(distanceY <= distanceX)
                    }
                    MotionEvent.ACTION_UP -> {
                        lastX = 0
                        lastY = 0
                        rv.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        }
    }
}