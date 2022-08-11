package com.woowa.banchan.data.local.datasource.recent

import com.woowa.banchan.data.local.entity.RecentDto

interface RecentDataSource {

    suspend fun getRecentList(): Result<List<RecentDto>>
}