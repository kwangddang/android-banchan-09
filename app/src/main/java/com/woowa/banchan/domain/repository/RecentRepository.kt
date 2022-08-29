package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Recent
import kotlinx.coroutines.flow.Flow

interface RecentRepository {

    suspend fun getRecentList(): Flow<List<Recent>>

    suspend fun insertRecent(recent: Recent)
}