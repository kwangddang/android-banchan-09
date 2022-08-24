package com.woowa.banchan.data.local.datasource.recent

import com.woowa.banchan.data.local.entity.RecentDto

interface RecentDataSource {

    suspend fun getRecentList(): List<RecentDto>
    suspend fun deleteRecent(recentDto: RecentDto)
    suspend fun insertRecent(recentDto: RecentDto)
}