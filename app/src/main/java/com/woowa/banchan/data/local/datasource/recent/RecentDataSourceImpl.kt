package com.woowa.banchan.data.local.datasource.recent

import com.woowa.banchan.data.local.dao.RecentDao
import com.woowa.banchan.data.local.entity.RecentDto
import java.util.*
import javax.inject.Inject

class RecentDataSourceImpl @Inject constructor(
    private val recentDao: RecentDao
) : RecentDataSource {

    override suspend fun getRecentList(): Result<List<RecentDto>> =
        runCatching {
            val list = recentDao.getRecentList().toMutableList()
            val curCalendar = Calendar.getInstance()
                .apply { time = Date() }
            val calendar = Calendar.getInstance()
            val retList = mutableListOf<RecentDto>()
            list.forEach {
                calendar.time = it.time
                if (calendar.get(Calendar.DAY_OF_WEEK) != curCalendar.get(Calendar.DAY_OF_WEEK))
                    recentDao.delete(it)
                else retList.add(it)
            }
            retList
        }

    override suspend fun insertRecent(recentDto: RecentDto): Result<Unit> =
        runCatching { recentDao.insert(recentDto) }
}