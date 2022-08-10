package com.woowa.banchan.di

import com.woowa.banchan.domain.usecase.food.GetBestFoodsUseCaseImpl
import com.woowa.banchan.domain.usecase.food.GetFoodsUseCaseImpl
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun provideGetFoodsUseCase(
        GetFoodsUseCaseImpl: GetFoodsUseCaseImpl
    ): GetFoodsUseCase

    @Singleton
    @Binds
    abstract fun provideGetBestFoodsUseCase(
        GetBestFoodsUseCaseImpl: GetBestFoodsUseCaseImpl
    ): GetBestFoodsUseCase
}