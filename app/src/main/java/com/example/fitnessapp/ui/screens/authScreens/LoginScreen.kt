@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.fitnessapp.ui.screens.authScreens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.platform.LocalContext
import com.example.fitnessapp.R
import com.example.fitnessapp.viewmodel.AuthViewModel
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val signInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Manejo del resultado de la actividad de Google Sign-In
        handleSignInResult(result, authViewModel, onLoginSuccess)
    }

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
            text = "Iniciar Sesión",
            fontSize = 32.sp,
            color = Color(0xFF24C4E6),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico", color = labelColor) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = labelColor,
                unfocusedLabelColor = labelColor,
                cursorColor = Color.White,
                containerColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(color = textColor),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = labelColor) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = labelColor,
                unfocusedLabelColor = labelColor,
                cursorColor = Color.White,
                containerColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(color = textColor),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de inicio de sesión
        if (isLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Button(
                onClick = {
                    isLoading = true
                    authViewModel.signInWithEmail(email, password) { success ->
                        isLoading = false
                        if (success) onLoginSuccess() else errorMessage = "Credenciales incorrectas."
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35B4CF))
            ) {
                Text("Iniciar Sesión", color = Color.Black)
            }
        }

        // Botón para iniciar sesión con Google
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { signInWithGoogle(context, signInLauncher) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB4437))
        ) {
            Text("Iniciar Sesión con Google", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Texto para ir a la pantalla de registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate", color = Color.White)
        }

        // Mensaje de error
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red)
        }
    }
}

fun signInWithGoogle(context: android.content.Context, launcher: ActivityResultLauncher<Intent>) {
    // Configuración de Google Sign-In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val signInIntent = googleSignInClient.signInIntent
    launcher.launch(signInIntent)
}

fun handleSignInResult(result: ActivityResult, authViewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    task.addOnCompleteListener { taskResult ->
        if (taskResult.isSuccessful) {
            val account = taskResult.result // Obtienes el GoogleSignInAccount aquí
            val idToken = account?.idToken
            idToken?.let {
                authViewModel.signInWithGoogle(it) { success ->
                    if (success) {
                        onLoginSuccess() // Navegar a la pantalla principal o el hogar
                    } else {
                        // Manejar error de autenticación
                    }
                }
            }
        } else {
            // Manejar fallo de Google Sign-In
        }
    }
}
