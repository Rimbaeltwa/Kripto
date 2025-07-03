package com.anadolubank.kripto
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.anadolubank.kripto.presentation.register.RegisterViewModel
import com.anadolubank.kripto.presentation.crypto_list.CryptoViewModel
import com.anadolubank.kripto.presentation.login.LoginViewModel
import com.anadolubank.kripto.presentation.navigation.AppNavGraph

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val cryptoViewModel: CryptoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavGraph(
                loginViewModel = loginViewModel,
                registerViewModel = registerViewModel,
                cryptoViewModel = cryptoViewModel
            )
        }
    }
}