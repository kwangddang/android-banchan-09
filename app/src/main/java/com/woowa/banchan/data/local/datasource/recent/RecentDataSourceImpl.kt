package com.woowa.banchan.data.local.datasource.recent

import com.woowa.banchan.data.local.dao.RecentDao
import com.woowa.banchan.data.local.entity.RecentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentDataSourceImpl @Inject constructor(
    private val recentDao: RecentDao
) : RecentDataSource {

    override suspend fun getRecentList(): Result<List<RecentDto>> =
        runCatching {
            withContext(Dispatchers.IO) {
                recentDao.getRecentList()
            }
        }

    override suspend fun deleteRecent(recentDto: RecentDto): Result<Unit> =
        runCatching { recentDao.delete(recentDto) }

    override suspend fun insertRecent(recentDto: RecentDto): Result<Unit> =
        runCatching { recentDao.insert(recentDto) }
}