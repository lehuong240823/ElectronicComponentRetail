package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Provider(
    val id: Int,
    val name: String? = null
)
