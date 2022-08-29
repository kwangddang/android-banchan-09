package com.woowa.banchan.data.local.datasource.recent

import com.woowa.banchan.data.local.entity.RecentDto
import kotlinx.coroutines.flow.Flow

interface RecentDataSource {
    suspend fun getRecentList(): Flow<List<RecentDto>>

    suspend fun deleteRecent(recentDto: RecentDto)

    suspend fun insertRecent(recentDto: RecentDto)
}