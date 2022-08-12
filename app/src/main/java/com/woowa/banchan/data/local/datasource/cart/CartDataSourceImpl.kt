package com.woowa.banchan.data.local.datasource.cart

import androidx.annotation.WorkerThread
import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.entity.CartDto
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val cartDao: CartDao
) : CartDataSource {

    @WorkerThread
    override suspend fun getCartList(): Result<List<CartDto>> =
        runCatching { cartDao.getCartList() }
}