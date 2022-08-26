package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.model.FoodItem

interface InsertCartUseCase {

    suspend fun insertCart(foodItem: FoodItem, totalCount: Int): Result<Unit>

    suspend fun insertCart(detailItem: DetailItem, title: String, totalCount: Int): Result<Unit>
}