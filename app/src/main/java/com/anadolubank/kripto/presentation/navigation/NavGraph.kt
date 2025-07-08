package com.anadolubank.kripto.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anadolubank.kripto.presentation.crypto_list.CryptoList
import com.anadolubank.kripto.presentation.login.LoginScreen
import com.anadolubank.kripto.presentation.login.LoginViewModel
import com.anadolubank.kripto.presentation.register.RegisterScreen
import com.anadolubank.kripto.presentation.register.RegisterViewModel
import com.anadolubank.kripto.presentation.crypto_list.CryptoViewModel


@Composable
fun AppNavGraph(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    cryptoViewModel: CryptoViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onRegisterClicked = {navController.navigate("register")},
                onLoginSuccess = {
                    navController.navigate("crypto") {

                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    // Kayıt işlemi başarılı olduktan sonra geri veya login ekranına yönlendirme yapılabilir
                    navController.popBackStack() //onLoginSuccessten bakarak yap. login edilince kriptoya yönlendirip incluesive kendine kadar hepesini siliyor.
                },
                onBackToLogin = {navController.popBackStack( )}//saveState = false

            )
        }

        composable("crypto"){
            CryptoList(
                viewModel = cryptoViewModel,
                onLogout = {
                    cryptoViewModel.logout()
                    loginViewModel.resetState()
                    navController.navigate("login"){
                        // Stack'i tamamen temizle
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

    }
}