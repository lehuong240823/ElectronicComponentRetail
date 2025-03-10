package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.example.project.core.BigDecimalSerializer

@Serializable
@JsonIgnoreUnknownKeys
data class OrderItem(
    val id: Int? = null,
    val order: Order? = null,
    val product: Product? = null,
    val productName: String? = null,
    val quantity: Int? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var price: BigDecimal? = null
)