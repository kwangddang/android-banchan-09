package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.BanchanDataBase.Companion.cartTable
import com.woowa.banchan.data.local.entity.CartDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert
    suspend fun insertCart(cartDto: CartDto)

    @Update
    suspend fun updateCart(cartDto: CartDto)

    @Query("SELECT * FROM $cartTable")
    fun getCartList(): Flow<List<CartDto>>

    @Query("DELETE FROM $cartTable WHERE hash = :hash")
    suspend fun deleteCart(hash: String)
}