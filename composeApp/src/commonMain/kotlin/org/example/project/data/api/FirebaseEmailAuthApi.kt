package org.example.project.data.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.project.core.API_KEY
import org.example.project.core.HttpClient
import org.example.project.domain.model.FirebaseEmailAuthRequest
import org.example.project.domain.model.FirebaseEmailAuthResponse
import org.example.project.domain.model.FirebaseSendEmailRequest
import org.example.project.domain.model.FirebaseSendEmailResponse

class FirebaseEmailAuthApi {
    suspend fun signUp(firebaseEmailAuthRequest: FirebaseEmailAuthRequest): FirebaseEmailAuthResponse {
        val endPoint = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${API_KEY}"
        return HttpClient.client.post(endPoint) {
            contentType(ContentType.Application.Json)
            setBody(firebaseEmailAuthRequest)
        }.body()
    }

    suspend fun signIn(firebaseEmailAuthRequest: FirebaseEmailAuthRequest): FirebaseEmailAuthResponse {
        val endPoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${API_KEY}"
        return HttpClient.client.post(endPoint) {
            contentType(ContentType.Application.Json)
            setBody(firebaseEmailAuthRequest)
        }.body()
    }

    suspend fun sendResetPasswordEmail(firebaseSendEmailRequest: FirebaseSendEmailRequest): FirebaseSendEmailResponse {
        val endPoint = "https://identitytoolkit.googleapis.com/v1/accounts:resetPassword?key=${API_KEY}"
        return HttpClient.client.post(endPoint) {
            contentType(ContentType.Application.Json)
            setBody(firebaseSendEmailRequest)
        }.body()
    }
}