package com.anadolubank.kripto.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.anadolubank.kripto.domain.repository.CryptoDetailRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.EntryPointAccessors
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CryptoDetailRepositoryEntryPoint {
    fun getCryptoDetailRepository(): CryptoDetailRepository
}

@Composable
fun AppNavGraph(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    cryptoViewModel: CryptoViewModel,
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
                onItemClick = {symbol ->
                    val encoded = Uri.encode(symbol)
                    navController.navigate("crypto_detail/$symbol")
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
                }
            )
        }

        composable(
            route = "crypto_detail/{symbol}",
            arguments = listOf(navArgument("symbol") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            // now symbol = backStackEntry.arguments?.getString("symbol")
            // will correctly be "0xBTC/BTC" (decoded for you)
            val detailViewModel: CryptoDetailViewModel =
                hiltViewModel(backStackEntry)

            CryptoDetailScreen(
                viewModel = detailViewModel,
                onBack = { navController.popBackStack() }
            )
        }


    }
}