package com.anadolubank.kripto.di

import android.content.Context

import com.anadolubank.kripto.data.CryptoDetailRepositoryImpl
import com.anadolubank.kripto.data.remote.CryptoApiService
import com.anadolubank.kripto.data.CryptoRepositoryImpl
import com.anadolubank.kripto.data.remote.CryptoDetailApiService
import com.anadolubank.kripto.domain.repository.CryptoDetailRepository
import com.anadolubank.kripto.domain.repository.CryptoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        private const val BASE_URL = "https://api.twelvedata.com/"

        @Provides @Singleton
        fun loggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        @Provides @Singleton
        fun apiKeyInterceptor(@ApplicationContext ctx: Context): ApiKeyInterceptor =
            ApiKeyInterceptor(ctx)

        @Provides @Singleton
        fun okHttpClient(
            logging: HttpLoggingInterceptor,
            apiKey: ApiKeyInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(apiKey)
                .addInterceptor(logging)
                .build()

        @Provides @Singleton
        fun retrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Provides @Singleton
        fun cryptoApiService(retrofit: Retrofit): CryptoApiService =
            retrofit.create(CryptoApiService::class.java)

        @Provides @Singleton
        fun cryptoDetailApiService(retrofit: Retrofit): CryptoDetailApiService =
            retrofit.create(CryptoDetailApiService::class.java)
    }

    @Binds @Singleton
    abstract fun bindCryptoRepo(
        impl: CryptoRepositoryImpl
    ): CryptoRepository

    @Binds @Singleton
    abstract fun bindCryptoDetailRepo(
        impl: CryptoDetailRepositoryImpl
    ): CryptoDetailRepository


}