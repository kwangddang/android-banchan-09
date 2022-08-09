package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Recent(
    @PrimaryKey val hash: String,
    @ColumnInfo(name = "time") val time: Date,
    @ColumnInfo(name = "n_price") val nPrice: Int,
    @ColumnInfo(name = "s_price") val sPrice: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
)