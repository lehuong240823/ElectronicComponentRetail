package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseResetPasswordLinkResponse(
    val email: String
)