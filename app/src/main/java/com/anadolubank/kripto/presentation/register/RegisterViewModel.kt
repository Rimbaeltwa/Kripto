package com.anadolubank.kripto.presentation.register
import com.anadolubank.kripto.domain.repository.AuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class RegisterViewModel @Inject constructor(
// enjeksiyonlar
private val authRepository: AuthRepository
) : ViewModel() {
    private  val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }
    fun resetState() {
        _uiState.value = RegisterUiState()
    }
    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun register() {
        if (_uiState.value.password != _uiState.value.confirmPassword) {
            _uiState.value = _uiState.value.copy(error = "Şifreler eşleşmeli!")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            //Log.d("RegisterViewModel", "Register işlemi başladı")
            val result = authRepository.register(
                _uiState.value.email,
                _uiState.value.password
            )
            if(result.isSuccess){
                _uiState.value = _uiState.value.copy(isLoading = false, isRegistered = true)
                Log.d("RegisterViewModel", "Register işlemi başarılı")

            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.localizedMessage ?: "Tekrar deneyin."
                )
            }


        }
    }

}