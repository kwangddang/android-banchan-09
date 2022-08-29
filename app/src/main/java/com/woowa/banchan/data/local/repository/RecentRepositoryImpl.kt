package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.recent.RecentDataSource
import com.woowa.banchan.data.local.entity.RecentDto
import com.woowa.banchan.data.local.entity.toRecentDto
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.repository.RecentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class RecentRepositoryImpl @Inject constructor(
    private val recentDataSource: RecentDataSource
) : RecentRepository {

    override suspend fun getRecentList(): Flow<List<Recent>> = withContext(Dispatchers.IO) {
        recentDataSource.getRecentList().map { list ->
            val curCalendar = Calendar.getInstance().apply { time = Date() }
            val calendar = Calendar.getInstance()
            val retList = mutableListOf<RecentDto>()
            list.forEach {
                calendar.time = it.time
                if (calendar.get(Calendar.DAY_OF_WEEK) != curCalendar.get(Calendar.DAY_OF_WEEK))
                    recentDataSource.deleteRecent(it)
                else retList.add(it)
            }
            retList.map { it.toRecent() }
        }
    }

    override suspend fun insertRecent(recent: Recent) = withContext(Dispatchers.IO) {
        recentDataSource.insertRecent(recent.toRecentDto())
    }
}