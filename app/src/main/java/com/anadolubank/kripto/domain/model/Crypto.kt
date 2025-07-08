package com.anadolubank.kripto.domain.model

data class CryptoCurrency(
    val symbol: String,
    val availableExchanges: List<String>,
    val baseCurrency: String,
    val quoteCurrency: String
)