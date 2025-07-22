package com.anadolubank.kripto.presentation.crypto_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadolubank.kripto.domain.repository.CryptoDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repo: CryptoDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // SavedStateHandle'dan symbol argümanını al
     val symbol: String =
        savedStateHandle.get<String>("symbol") ?: run {
            throw IllegalArgumentException("Symbol argument is required")
        }

    private val _uiState =
        MutableStateFlow<CryptoDetailsUiState>(CryptoDetailsUiState.Loading)

    val uiState: StateFlow<CryptoDetailsUiState> = _uiState

    init {
        fetchOhlc()
    }

    private fun fetchOhlc() {
        viewModelScope.launch {
            _uiState.value = CryptoDetailsUiState.Loading
            runCatching {
                repo.getOhlcBars(symbol)
            }.onSuccess { bars ->
                _uiState.value = CryptoDetailsUiState.Success(bars)
            }.onFailure { e ->
                _uiState.value =
                    CryptoDetailsUiState.Error(e.message.orEmpty())
            }
        }
    }
}