package com.woowa.banchan.data.local.datasource.cart

import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.entity.CartDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val cartDao: CartDao
) : CartDataSource {

    override suspend fun getCartList(): Result<Flow<Map<String, CartDto>>> =
        runCatching {
            cartDao.getCartList().map { list ->
                list.associateBy { cartDto -> cartDto.hash }
            }
        }

    override suspend fun updateCart(cartDto: CartDto): Result<Unit> =
        runCatching { cartDao.updateCart(cartDto) }

    override suspend fun deleteCart(hash: String): Result<Unit> =
        runCatching { cartDao.deleteCart(hash) }

    override suspend fun insertCart(cartDto: CartDto): Result<Unit> =
        runCatching { cartDao.insertCart(cartDto) }

}