package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun getCartList(): Result<Flow<Map<String, Cart>>>

    suspend fun updateCart(cart: Cart): Result<Unit>

    suspend fun deleteCart(cart: Cart): Result<Unit>

    suspend fun insertCart(cart: Cart): Result<Unit>
}