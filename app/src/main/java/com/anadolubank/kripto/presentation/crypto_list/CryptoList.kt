// app/src/main/java/com/anadolubank/kripto/presentation/crypto_list/CryptoList.kt
package com.anadolubank.kripto.presentation.crypto_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import com.anadolubank.kripto.R
import com.anadolubank.kripto.domain.model.CryptoCurrency

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoList(
    viewModel: CryptoViewModel,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }
    val fullList = (uiState as? CryptoUiState.Success)?.list.orEmpty()

    val filteredList = if(query.isBlank()){
        fullList
    } else {
        fullList.filter { crypto ->
            crypto.symbol.contains(query,ignoreCase = true) ||
                    crypto.baseCurrency.contains(query,ignoreCase = true) ||
                    crypto.quoteCurrency.contains(query,ignoreCase = true)
        }
    }

    val searchResults = if (query.isBlank()){
        emptyList()
    } else {
        fullList.filter { it.symbol.contains(query,ignoreCase = true) }
            .map { it.symbol }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor   = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = { Text( stringResource(R.string.crypto_list_title) ) },
                actions = {
                    CustomizableSearchBar(query = query,
                        onQueryChange = {query=it},
                        onResultClick = {query=it},
                        onSearch = {query=it},
                        searchResults = searchResults,
                        modifier = Modifier.weight(1.20f).fillMaxWidth())
                    IconButton(onClick = { viewModel.fetchCryptos() }) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.refresh))
                    }
                    MinimalDropdownMenu(onLogout = onLogout)


                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.fetchCryptos() },
                modifier = Modifier.fillMaxSize()
            ) {
                when (uiState) {
                    is CryptoUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is CryptoUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredList) { item ->
                                CryptoListItem(item)
                                HorizontalDivider()
                            }
                        }
                    }
                    is CryptoUiState.Error -> {
                        val msg = (uiState as CryptoUiState.Error).msg
                        Text(
                            text     = msg ?: stringResource(R.string.loading_error),
                            color    = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CryptoListItem(crypto: CryptoCurrency) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = crypto.symbol, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(4.dp))
        Text(
            text  = "${crypto.baseCurrency} / ${crypto.quoteCurrency}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun MinimalDropdownMenu(onLogout: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.more_options), modifier = Modifier.size(24.dp))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text(stringResource(R.string.logout)) }, onClick = {
                expanded = false
                onLogout()
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizableSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    onResultClick: (String) -> Unit,
    // Customization options
    placeholder: @Composable () -> Unit = { Text(stringResource(R.string.search_hint)) },
    leadingIcon: @Composable (() -> Unit)? = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_hint)) },
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingContent: (@Composable (String) -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,

    ) {
    // Track expanded state of search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            shape = RoundedCornerShape(16.dp),
            colors = SearchBarDefaults.colors(),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 8.dp, bottom = 4.dp, top = 2.dp)
                .heightIn(min = 56.dp)
                .clip(RoundedCornerShape(20.dp))
                .semantics { traversalIndex = 0f },

            inputField = {
                // Customizable input field implementation
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = {
                        onSearch(query)
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            // Show search results in a lazy column for better performance
            LazyColumn {
                items(count = searchResults.size) { index ->
                    val resultText = searchResults[index]
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = supportingContent?.let { { it(resultText) } },
                        leadingContent = leadingContent,
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .clickable {
                                onResultClick(resultText)
                                expanded = false
                            }
                            .fillMaxHeight()

                    )
                }
            }
        }
    }
}