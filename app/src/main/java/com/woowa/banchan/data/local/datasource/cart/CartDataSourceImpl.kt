package com.woowa.banchan.data.local.datasource.cart

import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.entity.CartDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val cartDao: CartDao
) : CartDataSource {

    override suspend fun getCartList(): Result<Flow<List<CartDto>>> =
        runCatching { cartDao.getCartList() }

    override suspend fun updateCart(cartDto: CartDto): Result<Unit> =
        runCatching { cartDao.updateCart(cartDto) }

    override suspend fun deleteCart(cartDto: CartDto): Result<Unit> =
        runCatching { cartDao.deleteCart(cartDto) }

    override suspend fun insertCart(cartDto: CartDto): Result<Unit> =
        runCatching { cartDao.insertCart(cartDto) }

}