package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.domain.UiState
import kotlinx.coroutines.flow.Flow

interface GetBestFoodsUseCase {

    suspend operator fun invoke(): Flow<UiState>
}