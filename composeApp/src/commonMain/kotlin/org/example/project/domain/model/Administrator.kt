package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.datetime.Instant
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.example.project.core.BigDecimalSerializer

@Serializable
@JsonIgnoreUnknownKeys
data class Administrator(
    val id: Int? = null,
    val account: Account? = null,
    val jobPosition: JobPosition? = null,
    val accessLevel: AccessLevel? = null
)