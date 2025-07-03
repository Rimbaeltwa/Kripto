package com.anadolubank.kripto.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.airbnb.lottie.compose.*
import com.anadolubank.kripto.R

@Composable
fun SplashScreen(
    onAnimationEnd: () -> Unit,
    // varsayılan false, böylece normal açılışta overlay olmaz
    useGreyOverlay: Boolean = false
) {
    // İçeride kullanacağımız Box yapısını duruma göre oluşturuyoruz
    val content: @Composable () -> Unit = {
        // Lottie animasyonunuz burada, örneğin:
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.loginanimation)
        )
        // Eğer animasyonu belirli sayıda tekrarlamak istiyorsanız iterations'ı ayarlayın
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
    }

    if (useGreyOverlay) {
        // Grey transparan arka planda, tam ekran bir Card ile
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    } else {
        // Normal düzen: animasyonu doğrudan ekranda ortada göster
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

    // Splash ekranı delay süresince (örneğin 2 saniye) gösterilsin,
    // ardından onAnimationEnd callback'i tetiklenecek.
    LaunchedEffect(Unit) {
        delay(2000L)
        onAnimationEnd()
    }
}