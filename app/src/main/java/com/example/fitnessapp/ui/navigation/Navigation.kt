package com.example.fitnessapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.ui.screens.authScreens.LoginScreen
import com.example.fitnessapp.ui.screens.authScreens.RegisterScreen
import com.example.fitnessapp.ui.screens.authScreens.WelcomeScreen
import com.example.fitnessapp.ui.screens.homeScreens.HomeScreen
import com.example.fitnessapp.viewmodel.AuthViewModel

@Composable
fun Navigation(authViewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()
    val isAuthenticated = authViewModel.isUserAuthenticated.collectAsState().value

    AppNavHost(
        navController = navController,
        isAuthenticated = isAuthenticated,
        authViewModel = authViewModel
    )
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    isAuthenticated: Boolean,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) "home" else "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
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
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen()
        }
    }
}
