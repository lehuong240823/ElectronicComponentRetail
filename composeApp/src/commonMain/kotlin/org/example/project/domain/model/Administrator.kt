package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Administrator(
    val id: Int? = null,
    val account: Account? = null,
    val jobPosition: String? = null,
    val status: String? = null
)
