package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseResetPasswordLinkRequest(
    val requestType: String = "PASSWORD_RESET",
    val email: String
)