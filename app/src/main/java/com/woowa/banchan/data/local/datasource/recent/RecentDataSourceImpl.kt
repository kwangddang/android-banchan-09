package com.woowa.banchan.data.local.datasource.recent

import com.woowa.banchan.data.local.dao.RecentDao
import com.woowa.banchan.data.local.entity.RecentDto
import javax.inject.Inject

class RecentDataSourceImpl @Inject constructor(
    private val recentDao: RecentDao
) : RecentDataSource {

    override suspend fun getRecentList(): List<RecentDto> =
        recentDao.getRecentList()

    override suspend fun deleteRecent(recentDto: RecentDto) {
        recentDao.delete(recentDto)
    }

    override suspend fun insertRecent(recentDto: RecentDto) {
        recentDao.insert(recentDto)
    }
}