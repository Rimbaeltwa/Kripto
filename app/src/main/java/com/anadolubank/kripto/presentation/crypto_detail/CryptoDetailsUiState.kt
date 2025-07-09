package com.anadolubank.kripto.presentation.crypto_detail

import com.anadolubank.kripto.domain.model.OhlcBar

sealed class CryptoDetailsUiState {
    object Loading : CryptoDetailsUiState()

    data class Success(
        val ohlcBars: List<OhlcBar>
    ) : CryptoDetailsUiState()

    data class Error(
        val message: String
    ) : CryptoDetailsUiState()
}