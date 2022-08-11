package com.woowa.banchan.di

import com.woowa.banchan.data.local.repository.CartRepositoryImpl
import com.woowa.banchan.data.remote.repository.FoodRepositoryImpl
import com.woowa.banchan.domain.repository.CartRepository
import com.woowa.banchan.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideFoodRepository(
        foodRepository: FoodRepositoryImpl
    ): FoodRepository

    @Singleton
    @Binds
    abstract fun provideCartRepository(
        cartRepository: CartRepositoryImpl
    ): CartRepository
}