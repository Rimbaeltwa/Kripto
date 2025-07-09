// data/remote/model/OhlcModels.kt
package com.anadolubank.kripto.data.remote

import com.google.gson.annotations.SerializedName
import com.anadolubank.kripto.domain.model.OhlcBar

data class OhlcResponse(
    val meta: MetaDto,
    val values: List<OhlcBarDto>,
    val status: String
)

data class MetaDto(
    val symbol: String,
    val interval: String,
    @SerializedName("currency_base")  val currencyBase:  String,
    @SerializedName("currency_quote") val currencyQuote: String,
    val exchange: String,
    val type:     String
)

data class OhlcBarDto(
    val datetime: String,
    val open:      String,
    val high:      String,
    val low:       String,
    val close:     String
)

// aynı dosyanın altında mapper fonksiyonu
fun OhlcBarDto.toDomain() = OhlcBar(
    date  = datetime,
    open  = open.toDouble(),
    high  = high.toDouble(),
    low   = low.toDouble(),
    close = close.toDouble()
)

fun OhlcResponse.toDomainList(): List<OhlcBar> =
    values.map(OhlcBarDto::toDomain)