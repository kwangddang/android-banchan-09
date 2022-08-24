package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.cart.CartDataSource
import com.woowa.banchan.data.local.entity.toCartDto
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
) : CartRepository {

    override suspend fun getCartList(): Flow<Map<String, Cart>> =
        cartDataSource.getCartList().map { map ->
            map.values.associate { cartDto ->
                cartDto.hash to cartDto.toCart()
            }
        }

    override suspend fun getCartCount(): Result<Flow<Int>> =
        runCatching { cartDataSource.getCartCount().getOrThrow() }

    override suspend fun updateCart(cart: Cart): Result<Unit> =
        runCatching { cartDataSource.updateCart(cart.toCartDto()) }

    override suspend fun deleteCart(hash: String): Result<Unit> =
        runCatching { cartDataSource.deleteCart(hash) }

    override suspend fun insertCart(cart: Cart): Result<Unit> =
        runCatching {
            cartDataSource.insertCart(cart.toCartDto()).getOrThrow()
        }

}