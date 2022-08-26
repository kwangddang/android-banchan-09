package com.woowa.banchan.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.entity.CartDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class CartDaoTest {

    private lateinit var database: BanchanDataBase

    @Before
    fun initDB() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BanchanDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDB() = database.close()

    @Test
    fun insertCartTest() = runTest {
        // GIVEN - insert a cart
        val cartDto = CartDto("Hash", true, 100, 100, "title", "image")
        database.cartDao().insertCart(cartDto)

        // WHEN - Get the cart by hash from the database
        val loaded = database.cartDao().getCartByHashForTest(cartDto.hash)

        // THEN - The loaded data contains the expected values
        assertThat(loaded as CartDto, notNullValue())
        assertThat(loaded.hash, `is`(cartDto.hash))
        assertThat(loaded.title, `is`(cartDto.title))

        // 고의로 에러내기
        //assertThat(loaded.hash, `is`(cartDto.copy(hash = "hash2").hash))
    }

    @Test
    fun updateCartTest() = runTest {
        val updateTitle = "title2"
        // GIVEN - cart inserted
        val cartDto = CartDto("Hash", true, 100, 100, "title", "image")
        database.cartDao().insertCart(cartDto)

        // WHEN - update Cart with title
        database.cartDao().updateCart(cartDto.copy(title = updateTitle))

        // THEN - The list is empty
        val loaded = database.cartDao().getCartByHashForTest("Hash")
        assertThat(loaded.hash, `is`(cartDto.hash))
        assertThat(loaded.title, `is`(updateTitle))
    }

    @Test
    fun deleteCartTest() = runTest {
        // GIVEN - cart inserted
        val cartDto = CartDto("Hash", true, 100, 100, "title", "image")
        database.cartDao().insertCart(cartDto)

        // WHEN - delete Cart with hash
        database.cartDao().deleteCart("Hash")

        // THEN - The list is empty
        val loaded = database.cartDao().getAllCartListForTest()
        assertThat(loaded.isEmpty(), `is`(true))
    }
}