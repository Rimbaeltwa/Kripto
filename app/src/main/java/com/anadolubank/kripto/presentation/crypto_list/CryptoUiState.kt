package com.anadolubank.kripto.presentation.crypto_list

import com.anadolubank.kripto.domain.model.CryptoCurrency

sealed class CryptoUiState {
    object Loading: CryptoUiState()
    data class Success(val list: List<CryptoCurrency>) : CryptoUiState()
    data class Error(val msg: String?) : CryptoUiState()
}