package com.anadolubank.kripto
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.getValue // <-- Add this import
import androidx.compose.runtime.setValue

import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.anadolubank.kripto.presentation.register.RegisterViewModel
import com.anadolubank.kripto.presentation.crypto_list.CryptoViewModel
import com.anadolubank.kripto.presentation.login.LoginViewModel

import com.anadolubank.kripto.presentation.navigation.AppNavGraph

import com.anadolubank.kripto.presentation.SplashScreen
import com.anadolubank.kripto.ui.theme.KriptoTheme

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val cryptoViewModel: CryptoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KriptoTheme { Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                var showSplashScreen by remember { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    delay(2500L)
                    showSplashScreen = false
                }
                if (showSplashScreen){
                    SplashScreen(onAnimationEnd = {showSplashScreen = false})
                }
                else{AppNavGraph(
                    loginViewModel = loginViewModel,
                    registerViewModel = registerViewModel,
                    cryptoViewModel = cryptoViewModel
                )}

            } }

        }
    }
}