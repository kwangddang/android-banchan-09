package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.cart.CartDataSource
import com.woowa.banchan.data.local.entity.toCart
import com.woowa.banchan.data.local.entity.toCartDto
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
) : CartRepository {

    override suspend fun getCartList(): Result<Flow<List<Cart>>> {
        val cartFlow = cartDataSource.getCartList().getOrThrow()
        return runCatching {
            cartFlow.map { list ->
                list.map { cartDto ->
                    cartDto.toCart()
                }
            }
        }
    }

    override suspend fun updateCart(cart: Cart): Result<Unit> =
        runCatching { cartDataSource.updateCart(cart.toCartDto()) }

    override suspend fun deleteCart(cart: Cart): Result<Unit> =
        runCatching { cartDataSource.deleteCart(cart.toCartDto()) }

    override suspend fun insertCart(cart: Cart): Result<Unit> =
        runCatching {
            cartDataSource.insertCart(cart.toCartDto()).getOrThrow()
        }

}