// domain/repository/CryptoDetailRepository.kt
package com.anadolubank.kripto.domain.repository

import com.anadolubank.kripto.domain.model.OhlcBar

interface CryptoDetailRepository {
    /** symbol için 1-week’lik time_series verisi döner */
    suspend fun getOhlcBars(
        symbol: String,
        interval: String = "1week"
    ): List<OhlcBar>
}