package com.example.fitnessapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.ui.screens.HomeScreens.HomeScreen

@Composable
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            LoginScreen(){navController.navigate("Home")}
        }

        composable ("Home") {
            HomeScreen()
        }
    }

}