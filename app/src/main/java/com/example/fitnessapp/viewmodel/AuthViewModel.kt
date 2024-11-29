package com.example.fitnessapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated

    init {
        checkUserAuthentication()
    }

    private fun checkUserAuthentication() {
        _isUserAuthenticated.value = auth.currentUser != null
    }

    fun signInWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                onResult(task.isSuccessful)
                checkUserAuthentication()
            }
        }
    }

    fun signUpWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                onResult(task.isSuccessful)
                checkUserAuthentication()
            }
        }
    }

    fun signInWithGoogle(idToken: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ingreso exitoso, el usuario ha sido autenticado.
                    onResult(true)
                } else {
                    // Error en el inicio de sesión.
                    onResult(false)
                }
                checkUserAuthentication()
            }
        }
    }
}
