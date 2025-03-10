package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.example.project.core.BigDecimalSerializer

@Serializable
@JsonIgnoreUnknownKeys
data class Account(
    val id: Int? = null,
    val email: String? = null,
    val username: String? = null,
    val phoneNumber: String? = null,
    val accountRole: AccountRole? = null,
    val accountStatus: AccountStatus? = null,
    val createTime: Instant? = null
)