package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.example.project.core.BigDecimalSerializer

@Serializable
@JsonIgnoreUnknownKeys
data class Voucher(
    val id: Int? = null,
    val code: String? = null,
    val voucherType: VoucherType? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var discountValue: BigDecimal? = null,
    val maxUses: Int? = null,
    val usedCount: Int? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var minOrder: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var maxDiscount: BigDecimal? = null,
    val validFrom: Instant? = null,
    val validUntil: Instant? = null,
    val isActive: Boolean? = null
)