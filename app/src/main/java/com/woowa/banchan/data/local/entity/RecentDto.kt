package com.woowa.banchan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.domain.model.Recent
import java.util.*

@Entity(tableName = BanchanDataBase.recentTable)
data class RecentDto(
    @PrimaryKey val hash: String,
    @ColumnInfo(name = "time") val time: Date,
    @ColumnInfo(name = "n_price") val nPrice: Int?,
    @ColumnInfo(name = "s_price") val sPrice: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
) {

    fun toRecent(): Recent = Recent(
        hash = hash,
        time = time,
        nPrice = nPrice,
        sPrice = sPrice,
        title = title,
        imageUrl = imageUrl
    )
}

fun Recent.toRecentDto(): RecentDto =
    RecentDto(
        hash,
        time,
        nPrice,
        sPrice,
        title,
        imageUrl
    )