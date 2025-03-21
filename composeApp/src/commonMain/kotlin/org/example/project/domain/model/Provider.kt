package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.serialization.ExperimentalSerializationApi
import org.example.project.core.BigDecimalSerializer

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Provider(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null
)