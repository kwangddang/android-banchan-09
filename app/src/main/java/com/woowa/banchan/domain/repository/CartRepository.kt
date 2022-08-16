package com.woowa.banchan.domain.repository

import com.woowa.banchan.domain.model.Cart

interface CartRepository {

    suspend fun getCartList(): Result<List<Cart>>

    suspend fun updateCart(cart: Cart): Result<Unit>

    suspend fun deleteCart(cart: Cart): Result<Unit>
}