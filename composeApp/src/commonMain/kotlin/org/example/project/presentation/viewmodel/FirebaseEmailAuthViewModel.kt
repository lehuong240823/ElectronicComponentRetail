package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.domain.model.*

class FirebaseEmailAuthViewModel(private val firebaseEmailAuthRepository: FirebaseEmailAuthRepository) {
    private val _firebaseEmailAuthResponse = mutableStateOf<FirebaseEmailAuthResponse?>(null)
    val firebaseEmailAuthResponse: State<FirebaseEmailAuthResponse?> get() = _firebaseEmailAuthResponse

    private val _firebaseResetPasswordLinkResponse = mutableStateOf<FirebaseResetPasswordLinkResponse?>(null)
    val firebaseResetPasswordLinkResponse: State<FirebaseResetPasswordLinkResponse?> get() = _firebaseResetPasswordLinkResponse

    private val _firebaseResetPasswordCodeResponse = mutableStateOf<FirebaseResetPasswordCodeResponse?>(null)
    val firebaseResetPasswordCodeResponse: State<FirebaseResetPasswordCodeResponse?> get() = _firebaseResetPasswordCodeResponse

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

    suspend fun sendResetPasswordLinkToEmail(firebaseResetPasswordLinkRequest: FirebaseResetPasswordLinkRequest) {
        val result = firebaseEmailAuthRepository.sendResetPasswordLinkToEmail(firebaseResetPasswordLinkRequest)
        if (result != null) {
            _firebaseResetPasswordLinkResponse.value = result
            _operationStatus.value = "Send Reset Password Link to Email Successfully"
        } else {
            _operationStatus.value = "Failed to Send Reset Password Link to Email"
        }
    }

    suspend fun sendResetPasswordCodeToEmail(firebaseResetPasswordCodeRequest: FirebaseResetPasswordCodeRequest) {
        val result = firebaseEmailAuthRepository.sendResetPasswordCodeToEmail(firebaseResetPasswordCodeRequest)
        if (result != null) {
            _firebaseResetPasswordCodeResponse.value = result
            _operationStatus.value = "Send Reset Password Code to Email Successfully"
        } else {
            _operationStatus.value = "Failed to Send Reset Password Code to Email"
        }
    }
}