package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseResetPasswordCodeResponse(
    val email: String,
    val requestType: String
)