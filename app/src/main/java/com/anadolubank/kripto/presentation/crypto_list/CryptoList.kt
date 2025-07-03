package com.anadolubank.kripto.presentation.crypto_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
//Lottie view splash ekle açılışa.
//ara ekranlarda çok performans yemicekse aralara da koyabilirsin. mesela login edilirken register edilirken.
//https://lottiefiles.com/search?q=crypto&category=animations
@Composable
fun CryptoList(
    viewModel: CryptoViewModel
){
    Card {
        var expanded by remember { mutableStateOf(false) }
        Column(Modifier.clickable{expanded = !expanded }) {
            AnimatedVisibility(expanded) {
                Text(
                    text = "Compose",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}
