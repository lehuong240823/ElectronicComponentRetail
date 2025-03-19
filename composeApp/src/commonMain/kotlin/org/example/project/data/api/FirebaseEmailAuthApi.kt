package org.example.project.data.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.project.FIREBASE_API_KEY
import org.example.project.core.HttpClient
import org.example.project.domain.model.*

class FirebaseEmailAuthApi {
    suspend fun signUp(firebaseEmailAuthRequest: FirebaseEmailAuthRequest): FirebaseEmailAuthResponse {
        val endPoint = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$FIREBASE_API_KEY"
        return HttpClient.client.post(endPoint) {
            contentType(ContentType.Application.Json)
            setBody(firebaseEmailAuthRequest)
        }.body()
    }

    suspend fun signIn(firebaseEmailAuthRequest: FirebaseEmailAuthRequest): FirebaseEmailAuthResponse {
        val endPoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$FIREBASE_API_KEY"
        return HttpClient.client.post(endPoint) {
            contentType(ContentType.Application.Json)
            setBody(firebaseEmailAuthRequest)
        }.body()
    }

    suspend fun sendResetPasswordLinkToEmail(firebaseResetPasswordLinkRequest: FirebaseResetPasswordLinkRequest): FirebaseResetPasswordLinkResponse {
        val endPoint = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=$FIREBASE_API_KEY"
        return HttpClient.client.post(endPoint) {
            contentType(ContentType.Application.Json)
            setBody(firebaseResetPasswordLinkRequest)
        }.body()
    }

    suspend fun sendResetPasswordCodeToEmail(firebaseResetPasswordCodeRequest: FirebaseResetPasswordCodeRequest): FirebaseResetPasswordCodeResponse {
        val endPoint = "https://identitytoolkit.googleapis.com/v1/accounts:resetPassword?key=$FIREBASE_API_KEY"
        return HttpClient.client.post(endPoint) {
            contentType(ContentType.Application.Json)
            setBody(firebaseResetPasswordCodeRequest)
        }.body()
    }
}