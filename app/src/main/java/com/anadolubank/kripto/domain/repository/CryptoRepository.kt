package com.anadolubank.kripto.domain.repository
import com.anadolubank.kripto.domain.model.StockInfo

interface CryptoRepository {
    suspend fun getCryptoList(): List<StockInfo>
}