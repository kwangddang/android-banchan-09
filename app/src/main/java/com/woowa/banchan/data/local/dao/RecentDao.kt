package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.entity.RecentDto
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentDto: RecentDto)

    @Update
    fun update(recentDto: RecentDto)

    @Delete
    fun delete(recentDto: RecentDto)

    @Query("SELECT * FROM ${BanchanDataBase.recentTable} ORDER BY time DESC")
    fun getRecentList(): Flow<List<RecentDto>>
}