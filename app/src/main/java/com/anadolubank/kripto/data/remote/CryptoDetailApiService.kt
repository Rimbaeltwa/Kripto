// data/remote/CryptoDetailApiService.kt
package com.anadolubank.kripto.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoDetailApiService {
    @GET("time_series")
    suspend fun getTimeSeries(
        @Query("symbol")     symbol: String,
        @Query("interval")   interval: String = "1week",
        @Query("start_date") startDate: String = "2021-03-01",
        @Query("outputsize") outputSize: Int    = 5000
    ): OhlcResponse
}