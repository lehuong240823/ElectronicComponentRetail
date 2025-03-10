package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class FirebaseEmailAuthResponse (
    val idToken: String,
    val email: String,
    val refreshToken: String? = null,
    val expiresIn: String? = null,
    val localId: String,
    val registered: Boolean
)