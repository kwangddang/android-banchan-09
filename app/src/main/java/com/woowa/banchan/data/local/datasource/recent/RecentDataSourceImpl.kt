package com.woowa.banchan.data.local.datasource.recent

import androidx.annotation.WorkerThread
import com.woowa.banchan.data.local.dao.RecentDao
import com.woowa.banchan.data.local.entity.RecentDto
import javax.inject.Inject

class RecentDataSourceImpl @Inject constructor(
    private val recentDao: RecentDao
) : RecentDataSource {

    @WorkerThread
    override suspend fun getRecentList(): Result<List<RecentDto>> =
        runCatching { recentDao.getRecentList() }
}