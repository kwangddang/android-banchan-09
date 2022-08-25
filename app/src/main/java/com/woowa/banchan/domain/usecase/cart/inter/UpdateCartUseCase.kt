package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.DetailItem

interface UpdateCartUseCase {

    suspend operator fun invoke(cart: Cart): Result<Unit>

    suspend operator fun invoke(
        detailItem: DetailItem,
        title: String,
        totalCount: Int
    ): Result<Unit>
}