package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun getCartList(): Flow<Map<String, Cart>>

    suspend fun getCartCount(): Result<Flow<Int>>

    suspend fun updateCart(vararg cart: Cart): Result<Unit>

    suspend fun deleteCart(hash: String): Result<Unit>

    suspend fun deleteCart(vararg cart: Cart): Result<Unit>

    suspend fun insertCart(cart: Cart): Result<Unit>
}