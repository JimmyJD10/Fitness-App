package com.example.fitnessapp.ui.screens.authScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Colores personalizados
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF101422), // Color superior
            Color(0xFF272B38)  // Color inferior
        )
    )

    val textColor = Color.White // Texto blanco
    val labelColor = Color.White // Etiquetas blancas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear una cuenta",
            fontSize = 32.sp,
            color = Color(0xFF24C4E6),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico", color = labelColor) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // Borde blanco al enfocarse
                unfocusedBorderColor = Color.Gray, // Borde gris cuando no tiene foco
                focusedLabelColor = labelColor, // Etiqueta blanca al enfocarse
                unfocusedLabelColor = labelColor, // Etiqueta blanca cuando no tiene foco
                cursorColor = Color.White, // Cursor blanco
                containerColor = Color.Transparent // Fondo transparente
            ),
            textStyle = LocalTextStyle.current.copy(color = textColor), // Texto blanco
            singleLine = true // No permite varias líneas
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = labelColor) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // Borde blanco al enfocarse
                unfocusedBorderColor = Color.Gray, // Borde gris cuando no tiene foco
                focusedLabelColor = labelColor, // Etiqueta blanca al enfocarse
                unfocusedLabelColor = labelColor, // Etiqueta blanca cuando no tiene foco
                cursorColor = Color.White, // Cursor blanco
                containerColor = Color.Transparent // Fondo transparente
            ),
            textStyle = LocalTextStyle.current.copy(color = textColor), // Texto blanco
            singleLine = true // No permite varias líneas
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña", color = labelColor) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White, // Borde blanco al enfocarse
                unfocusedBorderColor = Color.Gray, // Borde gris cuando no tiene foco
                focusedLabelColor = labelColor, // Etiqueta blanca al enfocarse
                unfocusedLabelColor = labelColor, // Etiqueta blanca cuando no tiene foco
                cursorColor = Color.White, // Cursor blanco
                containerColor = Color.Transparent // Fondo transparente
            ),
            textStyle = LocalTextStyle.current.copy(color = textColor), // Texto blanco
            singleLine = true // No permite varias líneas
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Button(
                onClick = {
                    if (password == confirmPassword) {
                        isLoading = true
                        authViewModel.signUpWithEmail(email, password) { success ->
                            isLoading = false
                            if (success) onRegisterSuccess() else errorMessage = "Error al registrar usuario."
                        }
                    } else {
                        errorMessage = "Las contraseñas no coinciden."
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35B4CF))
            ) {
                Text("Registrarse", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia Sesión", color = Color.White)
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red)
        }
    }
}


