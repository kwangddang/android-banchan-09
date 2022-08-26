package com.woowa.banchan.data.local.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.datasource.order.OrderDataSourceImpl
import com.woowa.banchan.data.local.entity.OrderDto
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
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class OrderDataSourceTest {

    private lateinit var orderDataSource: OrderDataSourceImpl
    private lateinit var database: BanchanDataBase

    private lateinit var newOrder: OrderDto
    private lateinit var orderItemList: List<OrderItemDto>

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BanchanDataBase::class.java
        ).allowMainThreadQueries().build()

        orderDataSource =
            OrderDataSourceImpl(database.orderDao(), database.orderItemDao(), database)


    }

    @After
    fun cleanUp() = database.close()

    @Test
    fun getTotalOrderListTest() = runTest { }

    @Test
    fun getOrderDetailTest() = runTest { }

    @Test
    fun getOrderTest() = runTest { }

    @Test
    fun insertNewOrderTest() = runTest { }

    @Test
    fun insertNewOrderItemTest() = runTest { }

    @Test
    fun insertNewOrderAndItemTest() = runTest {
        // 1. Given -
        newOrder = OrderDto(null, true, Date(), 10, 32, "title", "image")
        orderItemList = listOf(
            OrderItemDto(null, 0, 1, 100, "title1", "image1"),
            OrderItemDto(null, 0, 10, 10, "title2", "image2"),
            OrderItemDto(null, 0, 100, 1, "title3", "image3")
        )

        // 2. When -
        val orderId = orderDataSource.insertNewOrderAndItem(newOrder, orderItemList).getOrNull()?.id

        // 3. Then -
        MatcherAssert.assertThat(orderId, CoreMatchers.notNullValue())
        val loaded = orderDataSource.getOrderDetail(orderId!!).getOrNull()
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        orderItemList.forEachIndexed { index, orderItemDto ->
            MatcherAssert.assertThat(loaded!![index].id, CoreMatchers.`is`((index + 1).toLong()))
            MatcherAssert.assertThat(loaded[index].orderId, CoreMatchers.`is`(orderId))
            MatcherAssert.assertThat(loaded[index].title, CoreMatchers.`is`(orderItemDto.title))
        }
    }

    @Test
    fun updateOrderTest() = runTest { }

    @Test
    fun getOrderState() = runTest { }
}