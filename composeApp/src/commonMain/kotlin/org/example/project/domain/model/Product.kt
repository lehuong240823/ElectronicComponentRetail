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
data class Product(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var price: BigDecimal? = null,
    val stock: Int? = null,
    val productStatus: ProductStatus? = null,
    val category: Category? = null,
    val provider: Provider? = null,
)