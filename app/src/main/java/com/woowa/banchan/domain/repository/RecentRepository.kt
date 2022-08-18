package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Recent

interface RecentRepository {

    suspend fun getRecentList(): Result<List<Recent>>
    suspend fun insertRecent(recent: Recent): Result<Unit>
}