package com.anadolubank.kripto.domain.repository

import com.anadolubank.kripto.domain.model.CryptoCurrency
import retrofit2.http.GET

interface CryptoRepository {
    @GET("cryptoAssets")
    suspend fun getCryptoList(): List<CryptoCurrency>
}