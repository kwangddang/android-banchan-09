package com.woowa.banchan.data.local.datasource.recent

import com.woowa.banchan.data.local.entity.RecentDto

interface RecentDataSource {

    suspend fun getRecentList(): Result<List<RecentDto>>
    suspend fun deleteRecent(recentDto: RecentDto): Result<Unit>
    suspend fun insertRecent(recentDto: RecentDto): Result<Unit>
}