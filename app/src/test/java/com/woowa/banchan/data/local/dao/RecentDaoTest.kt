package com.woowa.banchan.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.entity.RecentDto
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
class RecentDaoTest {

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
    fun insertRecentTest() = runTest {
        // GIVEN - insert a recent
        val recentDto = RecentDto("Hash", Date(), 100, 100, "title", "image")
        database.recentDao().insert(recentDto)

        // WHEN - Get the recent by recentId from the database
        val loaded = database.recentDao().getRecentList()

        // THEN - The loaded data contains the expected values
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.isEmpty(), CoreMatchers.`is`(false))
        MatcherAssert.assertThat(loaded.first().hash, CoreMatchers.`is`(recentDto.hash))
        MatcherAssert.assertThat(loaded.first().title, CoreMatchers.`is`(recentDto.title))
    }

    @Test
    fun updateRecentTest() = runTest {
        val updateTitle = "title2"

        val recentDto = RecentDto("Hash", Date(), 100, 100, "title", "image")
        database.recentDao().insert(recentDto)

        database.recentDao().update(recentDto.copy(title = updateTitle))

        val loaded = database.recentDao().getRecentList()
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.isEmpty(), CoreMatchers.`is`(false))
        MatcherAssert.assertThat(loaded.first().hash, CoreMatchers.`is`(recentDto.hash))
        MatcherAssert.assertThat(loaded.first().title, CoreMatchers.`is`(updateTitle))
    }

    @Test
    fun deleteRecentTest() = runTest {
        val recentDto = RecentDto("Hash", Date(), 100, 100, "title", "image")
        database.recentDao().insert(recentDto)

        database.recentDao().delete(recentDto)

        val loaded = database.recentDao().getRecentList()
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.isEmpty(), CoreMatchers.`is`(true))
    }
}