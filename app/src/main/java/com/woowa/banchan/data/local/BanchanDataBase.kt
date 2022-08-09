package com.woowa.banchan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.dao.OrderItemDao
import com.woowa.banchan.data.local.dao.RecentDao
import com.woowa.banchan.data.local.entity.Cart
import com.woowa.banchan.data.local.entity.Order
import com.woowa.banchan.data.local.entity.OrderItem
import com.woowa.banchan.data.local.entity.Recent

@Database(entities = [Cart::class, Order::class, OrderItem::class, Recent::class], version = 1)
abstract class BanchanDataBase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun recentDao(): RecentDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao

    companion object {
        private var instance: BanchanDataBase? = null
        val databaseName: String = "banchan-database"

        fun getInstance(context: Context): BanchanDataBase {

            return Room.databaseBuilder(
                context,
                BanchanDataBase::class.java, databaseName
            ).build()
        }
    }
}