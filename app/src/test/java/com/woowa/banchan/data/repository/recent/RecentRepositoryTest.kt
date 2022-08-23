package com.woowa.banchan.data.repository.recent

import com.woowa.banchan.domain.model.Recent

interface RecentRepositoryTest {

    suspend fun getRecentList(): Result<List<Recent>>
    suspend fun insertRecent(recent: Recent): Result<Unit>
}