package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val id: Int? = null,
    val order: Order? = null,
    val product: Product? = null,
    val productName: String? = null,
    val quantity: Int? = null,
    val price: Int? = null
)
