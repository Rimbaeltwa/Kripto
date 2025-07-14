package com.anadolubank.kripto.di

import com.anadolubank.kripto.data.AuthRepositoryImpl
import com.anadolubank.kripto.data.CryptoDetailRepositoryImpl
import com.anadolubank.kripto.data.CryptoRepositoryImpl
import com.anadolubank.kripto.domain.repository.AuthRepository
import com.anadolubank.kripto.domain.repository.CryptoDetailRepository
import com.anadolubank.kripto.domain.repository.CryptoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds @Singleton
    abstract fun bindCryptoRepo(
        impl: CryptoRepositoryImpl
    ): CryptoRepository

    @Binds @Singleton
    abstract fun bindCryptoDetailRepo(
        impl: CryptoDetailRepositoryImpl
    ): CryptoDetailRepository
}