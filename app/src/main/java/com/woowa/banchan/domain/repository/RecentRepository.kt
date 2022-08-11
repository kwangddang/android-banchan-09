package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Recent

interface RecentRepository{

    suspend fun getRecentList(): Result<List<Recent>>
}