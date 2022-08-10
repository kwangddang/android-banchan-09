package com.woowa.banchan.di

import android.content.Context
import com.woowa.banchan.data.local.BanchanDataBase
import com.woowa.banchan.data.remote.datasource.food.FoodDataSource
import com.woowa.banchan.data.remote.datasource.food.FoodDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
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
}