package com.anadolubank.kripto.data
import com.anadolubank.kripto.data.remote.CryptoApiService
import com.anadolubank.kripto.domain.model.StockInfo
import com.anadolubank.kripto.domain.repository.CryptoRepository
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val apiService: CryptoApiService
): CryptoRepository{ override suspend fun getCryptoList(): List<StockInfo> = apiService.getCryptos() }