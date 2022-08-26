package com.woowa.banchan.data.local.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.datasource.recent.RecentDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@SmallTest
class RecentDataSourceTest {

    private lateinit var recentDataSource: RecentDataSourceImpl
    private lateinit var database: BanchanDataBase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BanchanDataBase::class.java
        ).allowMainThreadQueries().build()

        recentDataSource =
            RecentDataSourceImpl(database.recentDao())
    }

    @Test
    fun getRecentListTest() = runTest {}

    @Test
    fun deleteRecentTest() = runTest {}

    @Test
    fun insertRecentTest() = runTest {}
}