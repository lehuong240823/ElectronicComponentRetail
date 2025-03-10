package org.example.project.data.repository

import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.domain.model.AccessLevel
import org.example.project.domain.model.FirebaseEmailAuthRequest
import org.example.project.domain.model.FirebaseEmailAuthResponse
import org.example.project.domain.model.PaginatedResponse

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
}