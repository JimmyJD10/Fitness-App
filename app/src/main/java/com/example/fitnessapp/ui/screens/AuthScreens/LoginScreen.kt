package com.example.fitnessapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit //Lambda para ir a HomeScreen
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Inicio de sesión")

        Button(onClick = { navigateToHome() }) { //Boton querecibe el lambda para ira Homscreen
            Text(text = "Iniciar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNavigation() {
    LoginScreen(navigateToHome = {})
}
