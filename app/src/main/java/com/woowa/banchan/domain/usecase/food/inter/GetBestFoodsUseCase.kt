package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.data.remote.dto.BestFoodDto
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetBestFoodsUseCase {

    suspend operator fun invoke(): Flow<UiState<BestFoodDto>>
}