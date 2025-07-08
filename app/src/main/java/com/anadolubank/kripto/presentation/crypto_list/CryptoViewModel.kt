package com.anadolubank.kripto.presentation.crypto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadolubank.kripto.domain.repository.AuthRepository
import com.anadolubank.kripto.domain.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repo: CryptoRepository,
    private val authRepo: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CryptoUiState>(CryptoUiState.Loading)
    val uiState: StateFlow<CryptoUiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init { fetchCryptos() }

    fun fetchCryptos() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val list = repo.getCryptoList()
                Log.d("CryptoViewModel","Fetched ${list.size}")
                _uiState.value = CryptoUiState.Success(list)
            } catch(e: Exception) {
                Log.e("CryptoViewModel","Error",e)
                _uiState.value = CryptoUiState.Error(e.localizedMessage)
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun logout() = viewModelScope.launch {
        authRepo.logout()
        //_uiState.value = CryptoUiState.Loading // or CryptoUiState.Idle if you define one
        //_isRefreshing.value = false

    }
}