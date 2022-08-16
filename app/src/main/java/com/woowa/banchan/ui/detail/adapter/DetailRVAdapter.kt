package com.woowa.banchan.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemRvDetailBinding
import com.woowa.banchan.ui.detail.viewholder.DetailRVViewHolder

class DetailRVAdapter(private val urlList: List<String>) : RecyclerView.Adapter<DetailRVViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRVViewHolder {
        return DetailRVViewHolder(
            ItemRvDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailRVViewHolder, position: Int) {
        holder.bind(urlList[position])
    }

    override fun getItemCount() = urlList.size

}