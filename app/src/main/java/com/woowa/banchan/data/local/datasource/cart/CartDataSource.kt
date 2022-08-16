package com.woowa.banchan.data.local.datasource.cart

import com.woowa.banchan.data.local.entity.CartDto

interface CartDataSource {

    suspend fun getCartList(): Result<List<CartDto>>

    suspend fun updateCart(cartDto: CartDto): Result<Unit>

    suspend fun deleteCart(cartDto: CartDto): Result<Unit>
}