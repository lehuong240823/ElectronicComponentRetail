package org.example.project.data.repository

import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.domain.model.*

class FirebaseEmailAuthRepository(private val firebaseEmailAuthApi: FirebaseEmailAuthApi) {
    suspend fun signUp(firebaseEmailAuthRequest: FirebaseEmailAuthRequest): FirebaseEmailAuthResponse? {
        return try {
            firebaseEmailAuthApi.signUp(firebaseEmailAuthRequest)
        } catch (e: Exception) {
            println("Error sign up with email and password: ${e.message}")
            null
        }
    }

    suspend fun signIn(firebaseEmailAuthRequest: FirebaseEmailAuthRequest): FirebaseEmailAuthResponse? {
        return try {
            firebaseEmailAuthApi.signIn(firebaseEmailAuthRequest)
        } catch (e: Exception) {
            println("Error sign in with email and password: ${e.message}")
            null
        }
    }

    suspend fun sendResetPasswordLinkToEmail(firebaseResetPasswordLinkRequest: FirebaseResetPasswordLinkRequest): FirebaseResetPasswordLinkResponse? {
        return try {
            firebaseEmailAuthApi.sendResetPasswordLinkToEmail(firebaseResetPasswordLinkRequest)
        } catch (e: Exception) {
            println("Error send reset password link to email: ${e.message}")
            null
        }
    }

    suspend fun sendResetPasswordCodeToEmail(firebaseResetPasswordCodeRequest: FirebaseResetPasswordCodeRequest): FirebaseResetPasswordCodeResponse? {
        return try {
            firebaseEmailAuthApi.sendResetPasswordCodeToEmail(firebaseResetPasswordCodeRequest)
        } catch (e: Exception) {
            println("Error send reset password code to email: ${e.message}")
            null
        }
    }
}