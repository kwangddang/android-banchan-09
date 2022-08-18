package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.recent.RecentDataSource
import com.woowa.banchan.data.local.entity.toRecent
import com.woowa.banchan.data.local.entity.toRecentDto
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.repository.RecentRepository
import javax.inject.Inject

class RecentRepositoryImpl @Inject constructor(
    private val recentDataSource: RecentDataSource
) : RecentRepository {

    override suspend fun getRecentList(): Result<List<Recent>> {
        val list = recentDataSource.getRecentList().getOrThrow()
        return runCatching { list.map { it.toRecent() } }
    }

    override suspend fun insertRecent(recent: Recent): Result<Unit> =
        runCatching { recentDataSource.insertRecent(recent.toRecentDto()) }
}