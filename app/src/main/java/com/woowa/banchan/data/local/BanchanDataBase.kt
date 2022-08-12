package com.woowa.banchan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.dao.OrderItemDao
import com.woowa.banchan.data.local.dao.RecentDao
import com.woowa.banchan.data.local.entity.CartDto
import com.woowa.banchan.data.local.entity.OrderDto
import com.woowa.banchan.data.local.entity.OrderItemDto
import com.woowa.banchan.data.local.entity.RecentDto

@Database(
    entities = [CartDto::class, OrderDto::class, OrderItemDto::class, RecentDto::class],
    version = 1
)
@TypeConverters(DBConverter::class)
abstract class BanchanDataBase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun recentDao(): RecentDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao

    companion object {
        const val databaseName: String = "banchan-database"
        const val cartTable: String = "cart_table"
        const val recentTable: String = "recent_table"
        const val orderTable: String = "order_table"
        const val orderItemTable: String = "order_item_table"

        fun getInstance(context: Context): BanchanDataBase {

            return Room.databaseBuilder(
                context,
                BanchanDataBase::class.java, databaseName
            ).build()
        }
    }
}