package com.woowa.banchan.di

import com.woowa.banchan.domain.usecase.cart.GetCartListUseCaseImpl
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.food.GetBestFoodsUseCaseImpl
import com.woowa.banchan.domain.usecase.food.GetDetailFoodUseCaseImpl
import com.woowa.banchan.domain.usecase.food.GetFoodsUseCaseImpl
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetDetailFoodUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import com.woowa.banchan.domain.usecase.order.GetTotalOrderUseCaseImpl
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import com.woowa.banchan.domain.usecase.recent.GetRecentlyViewedFoodsUseCaseImpl
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
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

    @Singleton
    @Binds
    abstract fun provideGetCartListUseCase(
        GetCartListUseCase: GetCartListUseCaseImpl
    ): GetCartListUseCase

    @Singleton
    @Binds
    abstract fun provideGetRecentlyViewedFoodsUseCase(
        GetRecentlyViewedFoodsUseCase: GetRecentlyViewedFoodsUseCaseImpl
    ): GetRecentlyViewedFoodsUseCase

    @Singleton
    @Binds
    abstract fun provideGetDetailFoodUseCase(
        getDetailFood: GetDetailFoodUseCaseImpl
    ): GetDetailFoodUseCase

    @Singleton
    @Binds
    abstract fun provideGetTotalOrderUseCase(
        GetTotalOrderUseCaseImpl: GetTotalOrderUseCaseImpl
    ): GetTotalOrderUseCase
}