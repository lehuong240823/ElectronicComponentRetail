package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val account: Account? = null,
    val address: Map<String, Address>? = null,
    val payment: Map<String, Payment>? = null
)
