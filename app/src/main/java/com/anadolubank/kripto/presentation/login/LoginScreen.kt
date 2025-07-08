package com.anadolubank.kripto.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.material3.AlertDialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val view = LocalView.current

    var animateContent by remember { mutableStateOf(false) }

    val contentAlpha by animateFloatAsState(
        targetValue = if (animateContent) 1f else 0f, // 0'dan 1'e fade in
        animationSpec = tween(durationMillis = 800), // 800ms süreli animasyon
        label = "contentAlphaAnimation"
    )

    val hideKeyboard = rememberUpdatedState{
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    LaunchedEffect(Unit) {
        animateContent = true
    }
    LaunchedEffect(state.isLoggedIn) {
        if(state.isLoggedIn){
            hideKeyboard.value()
            delay(1500L)
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text="Welcome", fontSize = 48.sp, fontFamily = FontFamily.Default )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().alpha(contentAlpha),
            enabled = !state.isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().alpha(contentAlpha),
            enabled = !state.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.login()},
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth().alpha(contentAlpha)
        ) {
            Text("Login")
        }
        //dil değiştirme butonu için yazılar nasıl saklanmalı?
        Text(
            text = "Register",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {onRegisterClicked()
                            viewModel.resetState()}
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end=16.dp)
                .alpha(contentAlpha),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        if (state.error != null) {
            AlertDialog(onDismissRequest = {viewModel.clearError()},
                title = { Text( "Uyarı") },
                text = {  Text(state.error!!) //null olmadıgına emin olunmalı neden??
                },
                confirmButton = { TextButton(onClick = {viewModel.clearError()}){ Text("Tamam") } })


            //Text(
              //  text = state.error ?: "",
              //  color = MaterialTheme.colorScheme.error,
              //  modifier = Modifier.padding(top = 8.dp)
           // )
        }
    }
    LoginAnimationHandler(isLoggedIn = state.isLoggedIn, onLoginSuccess = onLoginSuccess) // bu tarz componentlar daha iyiyse artılılabilir sor??

}