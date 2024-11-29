package com.example.fitnessapp.ui.screens.authScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*

@Composable
fun InitialWelcomeScreen(
    onStartClick: () -> Unit // Cambiado a una función más genérica
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF101422), Color(0xFF272B38))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "FITNESS APP",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF24C4E6),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            val composition by rememberLottieComposition(LottieCompositionSpec.Asset("Inicio_run.json"))
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.sizeIn(maxHeight = 300.dp)
            )

            Button(
                onClick = { onStartClick() }, // Llama a la lógica de redirección
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24C4E6)),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Empezar", color = Color.Black)
            }
        }
    }
}
