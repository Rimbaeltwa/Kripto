package com.anadolubank.kripto.presentation.crypto_detail

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anadolubank.kripto.presentation.components.CryptoCandlestickChart
import com.anadolubank.kripto.presentation.crypto_list.CryptoUiState
import com.anadolubank.kripto.presentation.crypto_list.CryptoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(
    viewModel: CryptoDetailViewModel,
    cryptoListViewModel: CryptoViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val symbol = viewModel.symbol
    // 1) İçerik için bir LazyColumnState hatırla
    val listState = rememberLazyListState()
    val cryptoListState   by cryptoListViewModel.uiState.collectAsState()

    // 2) Listenin tepesinde mi diye bak; tepede ise gölge 0.dp, değilse 8.dp
    val targetElevation = if (listState.isLazyListAtTop()) 0.dp else 8.dp

    val selectedCrypto = (cryptoListState as? CryptoUiState.Success)
        ?.list
        ?.firstOrNull { it.symbol == symbol }
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
                    title = { Text(symbol) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector   = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                                tint          = Color.White
                            )
                        }
                    },
                    // İç yüzey transparan kalsın, gölge zaten Surface'da
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center

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
                    Log.d("asd" ,bars.toString())
                    LazyColumn(
                        state    = listState,
                        modifier = Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Add the candlestick chart as the first item

                        item {
                            selectedCrypto?.let { crypto ->
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text      = crypto.symbol,
                                        color     = Color.White,
                                        fontSize  = 28.sp,
                                        fontWeight= FontWeight.Bold
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text  = "${crypto.baseCurrency} / ${crypto.quoteCurrency}",
                                        color = Color.LightGray
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text  = "Exchanges: ${crypto.availableExchanges.joinToString()}",
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }

                        item {
                            CryptoCandlestickChart(
                                ohlcBars = bars,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }

                        // Add other content items if needed
                        items(bars.size) { _ ->
                            /* Other content items if needed */
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