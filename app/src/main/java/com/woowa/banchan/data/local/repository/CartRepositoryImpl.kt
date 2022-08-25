package com.woowa.banchan.data.local.repository

import com.woowa.banchan.data.local.datasource.cart.CartDataSource
import com.woowa.banchan.data.local.entity.toCartDto
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
) : CartRepository {

    override suspend fun getCartList(): Flow<Map<String, Cart>> =
        withContext(Dispatchers.IO) {
            cartDataSource.getCartList().map { map ->
                map.values.associate { cartDto ->
                    cartDto.hash to cartDto.toCart()
                }
            }
        }

    override suspend fun getCartCount(): Result<Flow<Int>> =
        withContext(Dispatchers.IO) {
            runCatching { cartDataSource.getCartCount() }
        }

    override suspend fun updateCart(vararg cart: Cart): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching { cartDataSource.updateCart(*cart.map { it.toCartDto() }.toTypedArray()) }
        }

    override suspend fun deleteCart(hash: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching { cartDataSource.deleteCart(hash) }
        }

    override suspend fun deleteCart(vararg cart: Cart): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching { cartDataSource.deleteCart(*cart.map { it.toCartDto() }.toTypedArray()) }
        }

    override suspend fun insertCart(cart: Cart): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                cartDataSource.insertCart(cart.toCartDto())
            }
        }

}