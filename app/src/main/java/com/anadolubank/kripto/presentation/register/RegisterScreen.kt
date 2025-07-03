// com.anadolubank.kripto.presentation.register.RegisterScreen.kt
package com.anadolubank.kripto.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit = {},
    onBackToLogin: () -> Unit = {}
) {
    // Observe the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Use a LaunchedEffect to react to isRegistered state change
    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            viewModel.resetState()
            onRegisterSuccess()
            // Optionally, reset isRegistered in ViewModel to prevent re-triggering if screen recomposes
            // viewModel.resetRegistrationState() // You'd add this function to your ViewModel
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kayıt Ol",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = uiState.email, // Use state from ViewModel
            onValueChange = viewModel::onEmailChange, // Call ViewModel's function
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.password, // Use state from ViewModel
            onValueChange = viewModel::onPasswordChange, // Call ViewModel's function
            label = { Text("Şifre") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.confirmPassword, // Use state from ViewModel
            onValueChange = viewModel::onConfirmPasswordChange, // Call ViewModel's function
            label = { Text("Şifre Tekrar") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.register() }, // Call ViewModel's register function
            enabled = !uiState.isLoading, // Disable button while loading
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(text = "Kaydı Tamamla")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {onBackToLogin()
                         viewModel.resetState()
                         }, modifier = Modifier.fillMaxWidth()) { Text("Giriş ekranına geri dönün") }

        if (uiState.error != null) { // Display error message from ViewModel
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}