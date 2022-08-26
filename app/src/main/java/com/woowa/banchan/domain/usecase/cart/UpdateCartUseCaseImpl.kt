package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.UpdateCartUseCase
import javax.inject.Inject

class UpdateCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : UpdateCartUseCase {

    override suspend operator fun invoke(vararg cart: Cart): Result<Unit> = cartRepository.updateCart(*cart)

    override suspend fun invoke(
        detailItem: DetailItem,
        title: String,
        totalCount: Int
    ): Result<Unit> = cartRepository.updateCart(detailItem.toCart(title, totalCount, true))
}