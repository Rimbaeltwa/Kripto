package com.anadolubank.kripto.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anadolubank.kripto.presentation.crypto_detail.CryptoDetailScreen
import com.anadolubank.kripto.presentation.crypto_detail.CryptoDetailViewModel
import com.anadolubank.kripto.presentation.crypto_list.CryptoList
import com.anadolubank.kripto.presentation.login.LoginScreen
import com.anadolubank.kripto.presentation.login.LoginViewModel
import com.anadolubank.kripto.presentation.register.RegisterScreen
import com.anadolubank.kripto.presentation.register.RegisterViewModel
import com.anadolubank.kripto.presentation.crypto_list.CryptoViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.anadolubank.kripto.event.logCryptoDetailViewed
import com.anadolubank.kripto.event.logScreenView
import com.google.firebase.analytics.FirebaseAnalytics



@Composable
fun AppNavGraph(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    cryptoViewModel: CryptoViewModel,
    firebaseAnalytics: FirebaseAnalytics
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onRegisterClicked = {
                    navController.navigate("register")
                    logScreenView(firebaseAnalytics, eventName = "register_screen_viewed","RegisterScreen", "RegisterScreen")

                                    },
                onLoginSuccess = {
                    navController.navigate("crypto") {

                        popUpTo("login") { inclusive = true }
                    }
                    logScreenView(firebaseAnalytics, eventName = "crypto_screen_viewed","LoginScreen", "LoginScreen")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    // Kayıt işlemi başarılı olduktan sonra geri veya login ekranına yönlendirme yapılabilir
                    navController.popBackStack()
                    logScreenView(firebaseAnalytics, eventName = "login_screen_viewed", "RegisterScreen", "RegisterScreen")
                },
                onBackToLogin = {navController.popBackStack( )
                    logScreenView(firebaseAnalytics, eventName = "login_screen_viewed", "LoginScreen", "LoginScreen")

                }//saveState = false

            )
        }

        composable("crypto"){
            CryptoList(
                onItemClick = {symbol ->
                    navController.navigate("crypto_detail/$symbol")
                    logScreenView(firebaseAnalytics, eventName = "crypto_detail_viewed", "CryptoListScreen", "CryptoListScreen")
                },
                viewModel = cryptoViewModel,
                onLogout = {
                    cryptoViewModel.logout()
                    loginViewModel.resetState()
                    navController.navigate("login"){
                        // Stack'i tamamen temizle
                        popUpTo(0)
                        launchSingleTop = true
                    }
                    logScreenView(firebaseAnalytics, eventName = "logout", "LoginScreen", "LoginScreen")

                }
            )

        }

        composable(
            route = "crypto_detail/{symbol}",
            arguments = listOf(navArgument("symbol") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val detailViewModel: CryptoDetailViewModel =
                hiltViewModel(backStackEntry)
            val cryptoSymbol = backStackEntry.arguments?.getString("symbol")
            CryptoDetailScreen(
                viewModel = detailViewModel,
                cryptoListViewModel = cryptoViewModel,
                onBack = { navController.popBackStack() }
            )

            logCryptoDetailViewed(firebaseAnalytics, cryptoSymbol)
            logScreenView(firebaseAnalytics, eventName = "crypto_detail_viewed","CryptoDetailScreen", "CryptoDetailScreen")
        }
    }


}




