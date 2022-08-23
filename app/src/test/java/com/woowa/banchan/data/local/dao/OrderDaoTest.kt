package com.woowa.banchan.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.entity.OrderDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class OrderDaoTest {

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
    fun insertOrderTest() = runTest {
        // GIVEN - insert a order
        val orderDto = OrderDto(null, true, Date(), 10, 32, "title", "image")
        database.orderDao().insertOrder(orderDto)

        // WHEN - Get the order by id from the database
        val loaded = database.orderDao().getOrder(1)

        // THEN - The loaded data contains the expected values
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.id, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loaded.title, CoreMatchers.`is`(orderDto.title))

        // 고의로 에러내기
        //assertThat(loaded.hash, `is`(cartDto.copy(hash = "hash2").hash))
    }

    @Test
    fun updateOrderTest() = runTest {
        // GIVEN - order inserted
        val orderDto = OrderDto(null, true, Date(), 10, 32, "title", "image")
        database.orderDao().insertOrder(orderDto)

        // WHEN - update Order's deliverState
        database.orderDao().updateOrder(1, false)

        // THEN - The list is empty
        val loaded = database.orderDao().getOrder(1)
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.id, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loaded.title, CoreMatchers.`is`(orderDto.title))
        MatcherAssert.assertThat(loaded.deliveryState, CoreMatchers.`is`(false))
    }

    @Test
    fun deleteOrderTest() = runTest {
        // GIVEN - cart inserted
        val orderDto = OrderDto(null, true, Date(), 10, 32, "title", "image")
        database.orderDao().insertOrder(orderDto)

        // WHEN - delete Cart with hash
        database.orderDao().deleteOrder(orderDto.copy(id = 1))

        // THEN - The list is empty
        val loaded = database.orderDao().getOrderListForTest()
        MatcherAssert.assertThat(loaded.isEmpty(), CoreMatchers.`is`(true))
    }
}
