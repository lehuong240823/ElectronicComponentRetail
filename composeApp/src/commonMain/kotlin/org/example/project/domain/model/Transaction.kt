package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.example.project.core.BigDecimalSerializer

@Serializable
data class Transaction(
    val id: Int? = null,
    val order: Order? = null,
    val paymentMethod: PaymentMethod? = null,
    val paymentMethodName: String? = null,
    val transactionNo: String? = null,
    val userPayment: UserPayment? = null,
    val transactionStatus: TransactionStatus? = null,
    val transactionTime: Instant? = null
)