package com.anadolubank.kripto.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false
){
    //solid principles gereği buradaki değişkenleri private'a geçir
    //ve public functionlarla dön.
}