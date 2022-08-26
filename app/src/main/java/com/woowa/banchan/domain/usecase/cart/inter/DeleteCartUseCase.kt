package com.woowa.banchan.domain.usecase.cart.inter

import com.woowa.banchan.domain.model.Cart

interface DeleteCartUseCase {

    suspend operator fun invoke(hash: String): Result<Unit>

    suspend operator fun invoke(vararg cart: Cart): Result<Unit>
}