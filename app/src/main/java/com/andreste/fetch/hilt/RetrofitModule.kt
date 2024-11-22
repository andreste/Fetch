package com.andreste.fetch.hilt

import com.andreste.fetch.services.HiringService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

private val json =
    Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    internal fun provideHiringService(retrofit: Retrofit): HiringService {
        return retrofit.create(HiringService::class.java)
    }
}