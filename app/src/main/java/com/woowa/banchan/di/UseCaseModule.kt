package com.woowa.banchan.di

import com.woowa.banchan.domain.usecase.cart.DeleteCartUseCaseImpl
import com.woowa.banchan.domain.usecase.cart.GetCartListUseCaseImpl
import com.woowa.banchan.domain.usecase.cart.InsertCartUseCaseImpl
import com.woowa.banchan.domain.usecase.cart.UpdateCartUseCaseImpl
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import com.woowa.banchan.domain.usecase.cart.inter.UpdateCartUseCase
import com.woowa.banchan.domain.usecase.food.GetBestFoodsUseCaseImpl
import com.woowa.banchan.domain.usecase.food.GetDetailFoodUseCaseImpl
import com.woowa.banchan.domain.usecase.food.GetFoodsUseCaseImpl
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetDetailFoodUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import com.woowa.banchan.domain.usecase.order.GetEachOrderUseCaseImpl
import com.woowa.banchan.domain.usecase.order.GetOrderDetailUseCaseImpl
import com.woowa.banchan.domain.usecase.order.GetTotalOrderUseCaseImpl
import com.woowa.banchan.domain.usecase.order.InsertCartToOrderUseCaseImpl
import com.woowa.banchan.domain.usecase.order.inter.GetEachOrderUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetOrderDetailUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import com.woowa.banchan.domain.usecase.order.inter.InsertCartToOrderUseCase
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
    abstract fun provideInsertCartUseCase(
        insertCartUseCase: InsertCartUseCaseImpl
    ): InsertCartUseCase

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

    @Singleton
    @Binds
    abstract fun provideGetOrderDetailUseCase(
        GetOrderDetailUseCaseImpl: GetOrderDetailUseCaseImpl
    ): GetOrderDetailUseCase

    @Singleton
    @Binds
    abstract fun provideUpdateCartUseCase(
        UpdateCartUseCaseImpl: UpdateCartUseCaseImpl
    ): UpdateCartUseCase

    @Singleton
    @Binds
    abstract fun provideDeleteCartUseCase(
        DeleteCartUseCaseImpl: DeleteCartUseCaseImpl
    ): DeleteCartUseCase

    @Singleton
    @Binds
    abstract fun provideInsertCartToOrderUseCase(
        InsertCartToOrderUseCaseImpl: InsertCartToOrderUseCaseImpl
    ): InsertCartToOrderUseCase

    @Singleton
    @Binds
    abstract fun provideGetEachOrderUseCase(
        GetEachOrderUseCaseImpl: GetEachOrderUseCaseImpl
    ): GetEachOrderUseCase
}