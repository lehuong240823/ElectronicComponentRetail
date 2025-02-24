package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val id: Int? = null,
    val user: User? = null,
    val vnpBankcode: String? = null,
    val accountNumber: String? = null,
    val isDefault: Boolean? = false
)
