package com.woowa.banchan.data.local.dao

import androidx.room.*
import com.woowa.banchan.data.local.BanchanDataBase.Companion.cartTable
import com.woowa.banchan.data.local.entity.CartDto

@Dao
interface CartDao {

    @Insert
    fun insert(cartDto: CartDto)

    @Update
    fun update(cartDto: CartDto)

    @Delete
    fun delete(cartDto: CartDto)

    @Query("SELECT * FROM $cartTable")
    fun getCartList(): List<CartDto>
}