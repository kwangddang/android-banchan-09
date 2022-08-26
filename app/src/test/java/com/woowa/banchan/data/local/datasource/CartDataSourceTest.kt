package com.woowa.banchan.data.local.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.datasource.cart.CartDataSourceImpl
import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class CartDataSourceTest {

    private lateinit var cartDataSource: CartDataSourceImpl
    private lateinit var database: BanchanDataBase



    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BanchanDataBase::class.java
        ).allowMainThreadQueries().build()

        cartDataSource = CartDataSourceImpl(database.cartDao())
    }

    @After
    fun cleanUp() = database.close()

    @Test
    fun getCartListTest() = runTest {}

    @Test
    fun updateCartTest() = runTest {}

    @Test
    fun deleteCartTest() = runTest {}

    @Test
    fun insertCartTest() = runTest {}
}