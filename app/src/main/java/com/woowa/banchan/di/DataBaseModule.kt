package com.woowa.banchan.di

import android.content.Context
import com.woowa.banchan.data.local.BanchanDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): BanchanDataBase {
        return BanchanDataBase.getInstance(context)
    }
}