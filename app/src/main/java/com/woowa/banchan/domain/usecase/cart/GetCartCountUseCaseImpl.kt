package com.woowa.banchan.domain.usecase.cart

import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.usecase.cart.inter.GetCartCountUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartCountUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : GetCartCountUseCase {

    override suspend fun invoke(): Result<Flow<Int>> = cartRepository.getCartCount()
}