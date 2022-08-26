package com.woowa.banchan.di

import com.woowa.banchan.BuildConfig
import com.woowa.banchan.data.remote.service.BanchanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(httpLoggingInterceptor: HttpLoggingInterceptor): Retrofit.Builder {
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
    }

    @Singleton
    @Provides
    fun provideBanchanApi(retrofitBuilder: Retrofit.Builder): BanchanService {
        return create(retrofitBuilder)
    }

    private fun create(retrofitBuilder: Retrofit.Builder): BanchanService {
        return retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(BanchanService::class.java)
    }
}