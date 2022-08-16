package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.Flow

interface InsertCartUseCase {
    suspend fun insertCart(foodItem: FoodItem, totalCount: Int): Flow<UiState<Unit>>
}