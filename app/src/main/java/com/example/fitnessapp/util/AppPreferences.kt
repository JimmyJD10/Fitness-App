package com.example.fitnessapp.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para DataStore
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class AppPreferences(private val context: Context) {

    companion object {
        private val IS_AUTHENTICATED = booleanPreferencesKey("is_authenticated")
    }

    // Leer el estado de autenticación
    val isAuthenticated: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_AUTHENTICATED] ?: false
        }

    // Guardar el estado de autenticación
    suspend fun setAuthenticated(authenticated: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_AUTHENTICATED] = authenticated
        }
    }
}
