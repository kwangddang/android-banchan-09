package com.woowa.banchan.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemVpDetailBinding
import com.woowa.banchan.ui.detail.viewholder.DetailVPViewHolder

class DetailVPAdapter(private val urlList: List<String>) : RecyclerView.Adapter<DetailVPViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailVPViewHolder {
        return DetailVPViewHolder(
            ItemVpDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailVPViewHolder, position: Int) {
        holder.bind(urlList[position])
    }

    override fun getItemCount() = urlList.size

}