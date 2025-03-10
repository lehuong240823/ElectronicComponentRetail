package org.example.project.data.api

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.example.project.core.HttpClient
import org.example.project.core.getUrl
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.ProductImage

class ProductImageApi {
    val endPoint = "/api/productImages"

    suspend fun getAllProductImages(): PaginatedResponse<ProductImage> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<ProductImage>>()
    }

    suspend fun getProductImage(productImageId: Int): ProductImage {
        return Json.decodeFromString<ProductImage>(HttpClient.client.get(urlString = getUrl("${endPoint}/$productImageId")).body())
    }
    
    suspend fun createProductImage(productImage: ProductImage): ProductImage {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productImage)
        }.body()
    }

    suspend fun updateProductImage(productImageId: Int, productImage: ProductImage): ProductImage {
        return HttpClient.client.put(getUrl("${endPoint}/$productImageId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productImage)
        }.body()
    }

    suspend fun deleteProductImage(productImageId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$productImageId"))
            true
        } catch (e: Exception) {
            println("Error deleting productImage: ${e.message}")
            false
        }
    }
}