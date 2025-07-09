// domain/repository/CryptoDetailRepository.kt
package com.anadolubank.kripto.data

import com.anadolubank.kripto.domain.repository.CryptoDetailRepository
import com.anadolubank.kripto.domain.model.OhlcBar

import com.anadolubank.kripto.data.remote.CryptoDetailApiService
import com.anadolubank.kripto.data.remote.toDomainList


import javax.inject.Inject

class CryptoDetailRepositoryImpl @Inject constructor(
    private val api: CryptoDetailApiService
) : CryptoDetailRepository {
    override suspend fun getOhlcBars(
        symbol: String,
        interval: String
    ): List<OhlcBar> =
        api.getTimeSeries(symbol, interval)
            .toDomainList()
}
