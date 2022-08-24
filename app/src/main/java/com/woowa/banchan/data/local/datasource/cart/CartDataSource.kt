package com.woowa.banchan.data.local.datasource.cart

import com.woowa.banchan.data.local.entity.CartDto
import kotlinx.coroutines.flow.Flow

interface CartDataSource {

    suspend fun getCartList(): Flow<Map<String, CartDto>>

    suspend fun getCartCount(): Result<Flow<Int>>

    suspend fun updateCart(cartDto: CartDto): Result<Unit>

    suspend fun deleteCart(hash: String): Result<Unit>

    suspend fun insertCart(cartDto: CartDto): Result<Unit>
}