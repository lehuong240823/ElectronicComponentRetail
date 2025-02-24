package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Int? = null,
    val email: String? = null,
    val role: String? = null,
    val username: String? = null,
    val name: String? = null,
    val createTime: String? = null,
    val phoneNumber: String? = null
)