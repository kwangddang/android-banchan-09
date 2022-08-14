package com.woowa.banchan.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemDetailBinding

class DetailVPAdapter(private val urlList: List<String>): RecyclerView.Adapter<DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
       holder.bind(urlList[position])
    }

    override fun getItemCount() = urlList.size

}

class DetailViewHolder(private val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(url: String) {
        binding.url = url
    }
}