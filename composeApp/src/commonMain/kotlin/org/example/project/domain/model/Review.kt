package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id: Int? = null,
    val user: User? = null,
    val product: Product? = null,
    val rating: Int? = null,
    val content: String? = null
)
