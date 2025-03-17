package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.domain.model.FirebaseEmailAuthRequest
import org.example.project.domain.model.FirebaseEmailAuthResponse

class FirebaseEmailAuthViewModel(private val firebaseEmailAuthRepository: FirebaseEmailAuthRepository) {
    private val _firebaseEmailAuthResponse = mutableStateOf<FirebaseEmailAuthResponse?>(null)
    val response: State<FirebaseEmailAuthResponse?> get() = _firebaseEmailAuthResponse

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun signUp(firebaseEmailAuthRequest: FirebaseEmailAuthRequest) {
        val result = firebaseEmailAuthRepository.signUp(firebaseEmailAuthRequest)
        if (result != null) {
            _firebaseEmailAuthResponse.value = result
            _operationStatus.value = "Sign Up With Email Successfully"
        } else {
            _operationStatus.value = "Failed to Create Sign Up With Email"
        }
    }

    suspend fun signIn(firebaseEmailAuthRequest: FirebaseEmailAuthRequest) {
        val result = firebaseEmailAuthRepository.signIn(firebaseEmailAuthRequest)
        if (result != null) {
            _firebaseEmailAuthResponse.value = result
            _operationStatus.value = "Sign In With Email Successfully"
        } else {
            _operationStatus.value = "Failed to Create Sign In With Email"
        }
    }

}