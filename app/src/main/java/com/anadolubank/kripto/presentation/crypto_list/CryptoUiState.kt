package com.anadolubank.kripto.presentation.crypto_list

import com.anadolubank.kripto.domain.model.StockInfo

sealed class CryptoUiState {
    object Loading: CryptoUiState()
    data class Success (val cryptoList: List<StockInfo>): CryptoUiState() //sealed classın alt sınıfları olarak tanımlanmalılar
    data class Error (val message: String?) : CryptoUiState()

}