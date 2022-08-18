package com.woowa.banchan.domain.usecase.food.inter

import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface GetFoodsUseCase {

    suspend operator fun invoke(type: String): Flow<UiState<List<FoodItem>>>
}