package com.anadolubank.kripto.presentation.login
import com.anadolubank.kripto.presentation.SplashScreen
import androidx.compose.runtime.*


@Composable
fun LoginAnimationHandler(
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit
) {
    if (isLoggedIn) {
        SplashScreen(onAnimationEnd = onLoginSuccess, useGreyOverlay = true)
    }
}