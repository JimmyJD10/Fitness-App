package com.example.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fitnessapp.ui.navigation.Navigation
import com.example.fitnessapp.util.AppPreferences
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar AppPreferences
        appPreferences = AppPreferences(applicationContext)

        setContent {
            Navigation(appPreferences = appPreferences)
        }
    }
}
