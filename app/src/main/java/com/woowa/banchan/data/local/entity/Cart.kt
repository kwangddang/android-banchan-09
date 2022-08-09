package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart(
    @PrimaryKey val hash: String,
    @ColumnInfo(name = "check_state") val checkState: Boolean,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
)
