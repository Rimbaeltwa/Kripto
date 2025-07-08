package com.anadolubank.kripto.data.remote

import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    val data: List<CryptoDto>,
    val status: String
)

data class CryptoDto(
    val symbol: String,
    @SerializedName("available_exchanges")
    val availableExchanges: List<String>,
    @SerializedName("currency_base")
    val baseCurrency: String,
    @SerializedName("currency_quote")
    val quoteCurrency: String
)