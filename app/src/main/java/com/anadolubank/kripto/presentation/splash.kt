package com.anadolubank.kripto.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.anadolubank.kripto.R // Make sure this is your app's R file
import kotlinx.coroutines.delay // Needed for a fixed duration splash

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Replace 'R.raw.splash_animation' with the actual name of your Lottie JSON file in res/raw
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loginanimation)) // Ensure your Lottie JSON is named splash_animation.json
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever, // Play indefinitely while visible
            speed = 1f
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp) // Adjust size as needed
        )

        // Use LaunchedEffect to handle the duration of the splash screen.
        // You can either wait for the animation to finish (if it's not infinite)
        // or just wait for a fixed delay.
        LaunchedEffect(Unit) { // Unit means it will run only once
            delay(1000L) // Show splash for 3 seconds (adjust as needed)
            onAnimationEnd() // Callback to signal splash is over
        }
    }
}