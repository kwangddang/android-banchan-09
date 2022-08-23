package com.woowa.banchan.data.repository.cart

import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.entity.toCart
import com.woowa.banchan.data.local.repository.CartRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class CartRepositoryTest {
    private lateinit var repository: CartRepositoryImpl
    private lateinit var dataSource: FakeCartDataSource

    @Before
    fun setup() {
        dataSource = FakeCartDataSource()
        repository = CartRepositoryImpl(dataSource)
    }

    @Test
    fun getCartList() = runTest {
        // 1. Given

        // 2. when
        // 3. Then
    }
}