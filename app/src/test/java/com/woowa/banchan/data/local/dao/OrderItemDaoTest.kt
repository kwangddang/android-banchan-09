package com.woowa.banchan.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.entity.OrderItemDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class OrderItemDaoTest {

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
    fun insertOrderItemTest() = runTest {
        // GIVEN - insert a cart
        val orderItemDto = OrderItemDto(null, 0, 100, 100, "title", "image")
        database.orderItemDao().insert(orderItemDto)

        // WHEN - Get the cart by hash from the database
        val loaded = database.orderItemDao().getOrderItemById(1)

        // THEN - The loaded data contains the expected values
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded?.id, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loaded?.title, CoreMatchers.`is`(orderItemDto.title))
    }
}