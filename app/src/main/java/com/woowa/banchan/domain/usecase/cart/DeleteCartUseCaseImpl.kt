package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import javax.inject.Inject

class DeleteCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : DeleteCartUseCase {

    override suspend operator fun invoke(hash: String): Result<Unit> =
        cartRepository.deleteCart(hash)

    override suspend fun invoke(vararg cart: Cart): Result<Unit> =
        cartRepository.deleteCart(*cart)

}