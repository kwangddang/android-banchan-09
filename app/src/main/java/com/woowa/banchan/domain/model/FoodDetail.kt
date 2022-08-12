package com.woowa.banchan.domain.model

import com.woowa.banchan.data.remote.dto.DetailItemDto

data class FoodDetail(
    val data: DetailItemDto,
    val hash: String
)
