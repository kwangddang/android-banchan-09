package com.woowa.banchan.domain.usecase.cart.inter

interface DeleteCartUseCase {

    suspend operator fun invoke(hash: String): Result<Unit>
}