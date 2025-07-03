package com.anadolubank.kripto.data.remote
import com.anadolubank.kripto.domain.model.StockInfo
import retrofit2.http.GET

interface CryptoApiService {
    @GET("cryptos")
    suspend fun getCryptos(): List<StockInfo>
}