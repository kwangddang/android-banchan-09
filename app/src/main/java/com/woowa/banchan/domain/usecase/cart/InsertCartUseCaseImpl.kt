package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import javax.inject.Inject

class InsertCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : InsertCartUseCase {
    override suspend fun insertCart(foodItem: FoodItem, totalCount: Int): Result<Unit> =
        cartRepository.insertCart(foodItem.toCart(totalCount, true))

    override suspend fun insertCart(
        detailItem: DetailItem,
        title: String,
        totalCount: Int
    ): Result<Unit> = cartRepository.insertCart(detailItem.toCart(title, totalCount, true))

}