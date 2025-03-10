package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.example.project.core.BigDecimalSerializer

@Serializable
@JsonIgnoreUnknownKeys
data class Order(
    val id: Int? = null,
    val user: User? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var amount: BigDecimal? = null,
    val orderStatus: OrderStatus? = null,
    val userAddress: UserAddress? = null,
    val address: String? = null,
    val trackingNumber: String? = null,
    val createdAt: Instant? = null,
    val voucher: Voucher? = null
)