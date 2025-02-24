package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: Int? = null,
    val user: User? = null,
    val name: String? = null,
    val street: String? = null,
    val city: String? = null,
    val province: String? = null,
    val isDefault: Boolean? = false
)
