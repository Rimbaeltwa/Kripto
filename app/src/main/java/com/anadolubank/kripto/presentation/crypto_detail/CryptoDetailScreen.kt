package com.anadolubank.kripto.presentation.crypto_detail

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(
    viewModel: CryptoDetailViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // 1) İçerik için bir LazyColumnState hatırla
    val listState = rememberLazyListState()

    // 2) Listenin tepesinde mi diye bak; tepede ise gölge 0.dp, değilse 8.dp
    val targetElevation = if (listState.isLazyListAtTop()) 0.dp else 8.dp

    // 3) Gölge değerini animate et
    val animatedElevation by targetElevation.animateElevation(durationMillis = 300)

    Scaffold(
        // tüm ekranın arka plan rengini #020818 yap
        containerColor = Color(0xFF020818),
        topBar = {
            // Surface ile gölge uyguluyoruz
            Surface(
                modifier        = Modifier.fillMaxWidth(),
                shadowElevation = animatedElevation,
                color           = Color.Black
            ) {
                TopAppBar(
                    title = { /* dilerseniz başlık koyun */ },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector   = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint          = Color.White
                            )
                        }
                    },
                    // İç yüzey transparan kalsın, gölge zaten Surface’da
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is CryptoDetailsUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color    = Color.White
                    )
                }
                is CryptoDetailsUiState.Success -> {
                    val bars = (uiState as CryptoDetailsUiState.Success).ohlcBars
                    // 4) Burada Vico Candlestick Chart’ınızı çağırın ve
                    //    içeriği scrollable hale getirmek için listState’i verin:
                    LazyColumn(
                        state    = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // örnek item
                        items(bars.size) { idx ->
                            /* ChartItem(bar = bars[idx]) */
                        }
                    }
                }
                is CryptoDetailsUiState.Error -> {
                    Text(
                        text = (uiState as CryptoDetailsUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

/**
 * LazyListState’in tepedeki durumunu kontrol eden yardımcı.
 */
fun androidx.compose.foundation.lazy.LazyListState.isLazyListAtTop() =
    firstVisibleItemIndex == 0 &&
            firstVisibleItemScrollOffset == 0

/**
 * Dp değerini animate etmek için helper.
 */
@Composable
fun Dp.animateElevation(
    durationMillis: Int = 300
): State<Dp> = animateDpAsState(
    targetValue = this,
    animationSpec = tween(durationMillis = durationMillis),
    label = "toolbar elevation"
)