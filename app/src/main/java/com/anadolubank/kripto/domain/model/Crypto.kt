package com.anadolubank.kripto.domain.model

data class StockInfo(
    val symbol: String,
    val name: String,
    val exchange: String,
    val micCode: String,
    val currency: String,
    val datetime: String,
    val timestamp: Long,
    val lastQuoteAt: Long,
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    val volume: String,
    val previousClose: String,
    val change: String,
    val percentChange: String,
    val averageVolume: String,
    val isMarketOpen: Boolean,
    val fiftyTwoWeek: FiftyTwoWeek
)

data class FiftyTwoWeek(
    val low: String,
    val high: String,
    val lowChange: String,
    val highChange: String,
    val lowChangePercent: String,
    val highChangePercent: String,
    val range: String
)


