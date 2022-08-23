package com.woowa.banchan.data.repository.cart

import com.woowa.banchan.data.local.datasource.cart.CartDataSource
import com.woowa.banchan.data.local.entity.CartDto
import com.woowa.banchan.domain.model.emptyCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCartDataSource : CartDataSource {

    override suspend fun getCartList(): Result<Flow<Map<String, CartDto>>> {
        return runCatching {
            flow {
                listOf(
                    emptyCart(),
                    emptyCart(),
                    emptyCart()
                ).associateBy { it.hash }
            }
        }
    }

    override suspend fun getCartCount(): Result<Flow<Int>> =
        runCatching { cartDao.getCartCount() }

    override suspend fun updateCart(cartDto: CartDto): Result<Unit> =
        runCatching { cartDao.updateCart(cartDto) }

    override suspend fun deleteCart(hash: String): Result<Unit> =
        runCatching { cartDao.deleteCart(hash) }

    override suspend fun insertCart(cartDto: CartDto): Result<Unit> =
        runCatching { cartDao.insertCart(cartDto) }

}