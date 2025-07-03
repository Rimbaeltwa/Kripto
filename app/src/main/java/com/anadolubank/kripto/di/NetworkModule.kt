package com.anadolubank.kripto.di
import com.anadolubank.kripto.data.remote.CryptoApiService
import com.anadolubank.kripto.data.CryptoRepositoryImpl
import com.anadolubank.kripto.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    companion object{
        private const val apiKey= "98d9732f41b849628e152a8b61d2423d"
        private const val BASE_URL = "https://api.twelvedata.com/"
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        @Provides
        @Singleton
        fun provideCryptoApiService(retrofit: Retrofit): CryptoApiService{
            return retrofit.create(CryptoApiService::class.java)
        }
    }
    @Binds
    @Singleton
    abstract fun bindCryptoRepository(cryptoRepositoryImpl: CryptoRepositoryImpl) : CryptoRepository


}