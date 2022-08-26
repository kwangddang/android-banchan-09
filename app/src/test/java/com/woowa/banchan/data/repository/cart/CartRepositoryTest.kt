package com.woowa.banchan.data.repository.cart

import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.datasource.cart.CartDataSource
import com.woowa.banchan.data.local.repository.CartRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class CartRepositoryTest {
    private lateinit var repository: CartRepositoryImpl
    private lateinit var dataSource: CartDataSource

    @Before
    fun setup() {
        dataSource = FakeCartDataSource()
        repository = CartRepositoryImpl(dataSource)
    }

    @Test
    fun getCartList() = runTest {
        // 1. Given

        // 2. when
        val loaded = repository.getCartList().getOrNull()?.first()
        // 3. Then
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded?.size, CoreMatchers.`is`(3))
    }
}