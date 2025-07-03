package com.anadolubank.kripto.presentation.crypto_list
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.anadolubank.kripto.domain.repository.CryptoRepository
import com.anadolubank.kripto.presentation.register.RegisterUiState
import com.anadolubank.kripto.presentation.crypto_list.CryptoUiState
@HiltViewModel

class CryptoViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow<CryptoUiState>(CryptoUiState.Loading)
    val uiState: StateFlow<CryptoUiState> = _uiState


}