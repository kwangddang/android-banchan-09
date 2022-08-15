package com.woowa.banchan.di

import com.woowa.banchan.data.local.datasource.cart.CartDataSource
import com.woowa.banchan.data.local.datasource.cart.CartDataSourceImpl
import com.woowa.banchan.data.local.datasource.order.OrderDataSource
import com.woowa.banchan.data.local.datasource.order.OrderDataSourceImpl
import com.woowa.banchan.data.local.datasource.recent.RecentDataSource
import com.woowa.banchan.data.local.datasource.recent.RecentDataSourceImpl
import com.woowa.banchan.data.remote.datasource.food.FoodDataSource
import com.woowa.banchan.data.remote.datasource.food.FoodDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun provideFoodDatasource(
        foodDataSourceImpl: FoodDataSourceImpl
    ): FoodDataSource

    @Singleton
    @Binds
    abstract fun provideCartDatasource(
        cartDataSource: CartDataSourceImpl
    ): CartDataSource

    @Singleton
    @Binds
    abstract fun provideRecentDatasource(
        recentDataSource: RecentDataSourceImpl
    ): RecentDataSource

    @Singleton
    @Binds
    abstract fun provideOrderDataSource(
        orderDataSource: OrderDataSourceImpl
    ): OrderDataSource
}