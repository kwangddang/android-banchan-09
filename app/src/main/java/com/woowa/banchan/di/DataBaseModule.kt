package com.woowa.banchan.di

import android.content.Context
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.local.dao.CartDao
import com.woowa.banchan.data.local.dao.OrderDao
import com.woowa.banchan.data.local.dao.OrderItemDao
import com.woowa.banchan.data.local.dao.RecentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): BanchanDataBase {
        return BanchanDataBase.getInstance(context)
    }

    @Provides
    fun provideCartDao(dataBase: BanchanDataBase): CartDao {
        return dataBase.cartDao()
    }

    @Provides
    fun provideRecentDao(dataBase: BanchanDataBase): RecentDao {
        return dataBase.recentDao()
    }

    @Provides
    fun provideOrderDao(dataBase: BanchanDataBase): OrderDao {
        return dataBase.orderDao()
    }

    @Provides
    fun provideOrderItemDao(dataBase: BanchanDataBase): OrderItemDao {
        return dataBase.orderItemDao()
    }
}