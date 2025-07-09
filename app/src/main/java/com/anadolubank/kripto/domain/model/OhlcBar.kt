// domain/model/OhlcBar.kt
package com.anadolubank.kripto.domain.model

data class OhlcBar(
    val date:  String,
    val open:  Double,
    val high:  Double,
    val low:   Double,
    val close: Double
)