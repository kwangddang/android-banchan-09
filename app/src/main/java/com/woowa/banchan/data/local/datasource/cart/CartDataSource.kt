package com.woowa.banchan.data.local.datasource.cart

import com.woowa.banchan.data.local.entity.CartDto

interface CartDataSource {

    fun getCartList(): Result<List<CartDto>>
}