package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.cart.CartDataSource
import com.woowa.banchan.data.local.entity.toCart
import com.woowa.banchan.data.local.entity.toCartDto
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
) : CartRepository {

    override suspend fun getCartList(): Result<List<Cart>> {
        val list = cartDataSource.getCartList().getOrThrow()
        return runCatching { list.map { it.toCart() } }
    }

    override suspend fun insertCart(cart: Cart): Result<Unit> {
        return kotlin.runCatching { cartDataSource.insertCart(cart.toCartDto()) }
    }
}