package com.anadolubank.kripto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.util.Log
import androidx.compose.runtime.getValue
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessaging // FirebaseMessaging import'ını ekleyin!

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val cryptoViewModel: CryptoViewModel by viewModels()
    private val firebaseAnalytics: FirebaseAnalytics by lazy { Firebase.analytics }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // FCM Kayıt Jetonunu almak (sadece loglamak için, sunucuya göndermenize gerek yok)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM kayıt jetonu alınamadı", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d(TAG, "FCM Kayıt Jetonu (sadece bilgi amaçlı): $token")
        }

        // Genel Duyuru Konusuna Otomatik Abone Olma
        // "genel_duyurular" yerine istediğiniz bir konu adını kullanabilirsiniz.
        val generalTopic = "genel_duyurular"
        FirebaseMessaging.getInstance().subscribeToTopic(generalTopic)
            .addOnCompleteListener { task ->
                var msg = "Genel Duyurular Konusuna Abone Olundu: $generalTopic"
                if (!task.isSuccessful) {
                    msg = "Genel Duyurular Konusuna Abone Olunamadı: $generalTopic"
                }
                Log.d(TAG, msg)
            }
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "main_activity")
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        Log.d(TAG, "Firebase Analytics: SCREEN_VIEW olayı kaydedildi.")

        setContent {
            KriptoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var showSplashScreen by remember { mutableStateOf(true) }
                    LaunchedEffect(Unit) {
                        delay(2500L) // SplashScreen süresi
                        showSplashScreen = false
                    }

                    if (showSplashScreen) {
                        SplashScreen(onAnimationEnd = { showSplashScreen = false })
                    } else {
                        AppNavGraph(
                            loginViewModel = loginViewModel,
                            registerViewModel = registerViewModel,
                            cryptoViewModel = cryptoViewModel,
                            firebaseAnalytics = firebaseAnalytics
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}