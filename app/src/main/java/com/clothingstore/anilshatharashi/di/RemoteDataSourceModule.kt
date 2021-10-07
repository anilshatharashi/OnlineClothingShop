package com.clothingstore.anilshatharashi.di

import com.clothingstore.anilshatharashi.BuildConfig
import com.clothingstore.anilshatharashi.data.repository.remote.ClothingListRemoteDataSource
import com.clothingstore.anilshatharashi.data.repository.remote.ClothingListRemoteDataSourceImpl
import com.clothingstore.anilshatharashi.data.repository.remote.ClothingListApi
import com.clothingstore.anilshatharashi.platform.NetworkHandler
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun providesClothingListRemoteDataSourceImpl(
        networkHandler: NetworkHandler,
        clothingApi: ClothingListApi
    ): ClothingListRemoteDataSource =
        ClothingListRemoteDataSourceImpl(networkHandler, clothingApi)

    @Provides
    @Singleton
    fun providesGson(): Gson = Gson()

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesClothingApi(retrofit: Retrofit): ClothingListApi =
        retrofit.create(ClothingListApi::class.java)
}