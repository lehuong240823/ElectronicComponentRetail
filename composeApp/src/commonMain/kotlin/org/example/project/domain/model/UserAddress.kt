package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.example.project.core.BigDecimalSerializer

@Serializable
@JsonIgnoreUnknownKeys
data class UserAddress(
    val id: Int? = null,
    val user: User? = null,
    val name: String? = null,
    val street: String? = null,
    val ward: String? = null,
    val commune: String? = null,
    val district: String? = null,
    val city: String? = null,
    val province: String? = null,
    val postalCode: String? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var latitute: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var longtitude: BigDecimal? = null,
    val isDefault: Boolean? = false
)