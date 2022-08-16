package com.woowa.banchan.data.local.datasource.cart

import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.entity.CartDto
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val cartDao: CartDao
) : CartDataSource {

    override suspend fun getCartList(): Result<List<CartDto>> =
        runCatching { cartDao.getCartList() }

    override suspend fun insertCart(cartDto: CartDto) {
        cartDao.insertCart(cartDto)
    }
}