package com.anadolubank.kripto.data.remote

import retrofit2.http.GET

interface CryptoApiService {
    @GET("cryptocurrencies")
    suspend fun getCryptocurrencies(): CryptoResponse
}