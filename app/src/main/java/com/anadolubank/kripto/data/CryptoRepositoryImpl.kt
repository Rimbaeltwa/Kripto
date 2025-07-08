package com.anadolubank.kripto.data

import com.anadolubank.kripto.data.remote.CryptoApiService
import com.anadolubank.kripto.domain.model.CryptoCurrency
import com.anadolubank.kripto.domain.repository.CryptoRepository
import android.util.Log
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CryptoApiService
) : CryptoRepository {
    override suspend fun getCryptoList(): List<CryptoCurrency> {
        val resp = api.getCryptocurrencies()
        Log.d("CryptoRepo", "API status=${resp.status}, count=${resp.data.size}")
        return resp.data.map { dto ->
            CryptoCurrency(
                symbol = dto.symbol,
                availableExchanges = dto.availableExchanges,
                baseCurrency = dto.baseCurrency,
                quoteCurrency = dto.quoteCurrency
            )
        }
    }
}