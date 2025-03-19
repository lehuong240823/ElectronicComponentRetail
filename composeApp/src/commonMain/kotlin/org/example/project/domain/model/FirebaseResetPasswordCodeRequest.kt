package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseResetPasswordCodeRequest(
    val oobCode: String,
    val newPassword: String
)