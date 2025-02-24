package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val category: Category,
    val provider: Provider,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    val quantity: Int,
    val brand: String? = null,
    val origin: String? = null,
    val picture: Map<String, List<String>>? = null
)
