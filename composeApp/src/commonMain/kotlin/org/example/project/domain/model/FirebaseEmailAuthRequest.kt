package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class FirebaseEmailAuthRequest (
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)