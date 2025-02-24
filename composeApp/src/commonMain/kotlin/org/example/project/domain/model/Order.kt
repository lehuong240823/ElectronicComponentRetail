package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Int,
    val orderTime: String? = null,
    val user: User? = null,
    val address: String,
    val phoneNumber: String,
    val status: String
)
