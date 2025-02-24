package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String? = null,
    val picture: String? = null
)
