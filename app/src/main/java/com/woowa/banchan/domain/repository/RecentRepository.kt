package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Recent

interface RecentRepository {

    suspend fun getRecentList(): List<Recent>
    suspend fun insertRecent(recent: Recent)
}