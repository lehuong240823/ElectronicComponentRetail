package org.example.project.domain.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class FirebaseResetPasswordLinkRequest(
    val email: String,
    val requestType: String = "PASSWORD_RESET"
)