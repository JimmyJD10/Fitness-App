package com.example.fitnessapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.ui.screens.authScreens.InitialWelcomeScreen
import com.example.fitnessapp.ui.screens.authScreens.LoginScreen
import com.example.fitnessapp.ui.screens.authScreens.RegisterScreen
import com.example.fitnessapp.ui.screens.homeScreens.HomeScreen
import com.example.fitnessapp.util.AppPreferences
import com.example.fitnessapp.viewmodel.AuthViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun Navigation(authViewModel: AuthViewModel = viewModel(), appPreferences: AppPreferences) {
    val navController = rememberNavController()

    // Obtener el estado de autenticación desde DataStore
    val isAuthenticated by appPreferences.isAuthenticated.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = "initialWelcome"
    ) {
        composable("initialWelcome") {
            InitialWelcomeScreen(
                onStartClick = {
                    if (isAuthenticated) {
                        navController.navigate("home") {
                            popUpTo("initialWelcome") { inclusive = true }
                        }
                    } else {
                        navController.navigate("login") {
                            popUpTo("initialWelcome") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    // Guardar autenticación en DataStore
                    runBlocking { appPreferences.setAuthenticated(true) }
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = {
                    // Guardar autenticación en DataStore
                    runBlocking { appPreferences.setAuthenticated(true) }
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        composable("home") {
            HomeScreen()
        }
    }
}
