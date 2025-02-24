package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: Int? = null,
    val order: Order? = null,
    val transactionTime: String? = null,
    val status: String? = null,
)
