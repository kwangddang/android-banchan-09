package com.woowa.banchan.data.local.datasource.cart

import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.entity.CartDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val cartDao: CartDao
) : CartDataSource {

    override suspend fun getCartList(): Flow<Map<String, CartDto>> =
        cartDao.getCartList().map { list -> list.associateBy { cartDto -> cartDto.hash } }

    override suspend fun getCartCount(): Flow<Int> = cartDao.getCartCount()

    override suspend fun updateCart(vararg cartDto: CartDto) {
        cartDao.updateCart(*cartDto)
    }

    override suspend fun deleteCart(hash: String) {
        cartDao.deleteCart(hash)
    }

    override suspend fun deleteCart(vararg cartDto: CartDto) {
        cartDao.deleteCart(*cartDto)
    }

    override suspend fun insertCart(cartDto: CartDto) {
        cartDao.insertCart(cartDto)
    }

}