package org.example.project.data.api

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import org.example.project.getPageSize
import org.example.project.core.HttpClient
import org.example.project.BASE_URL
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.ProductImage

class ProductImageApi {
    val endPoint = "/api/product-images"

    suspend fun getAllProductImages(currentPage: Int): PaginatedResponse<ProductImage> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getProductImage(productImageId: Int): ProductImage {
        return HttpClient.client.get("$BASE_URL${endPoint}/${productImageId}").body()
    }
    
    suspend fun createProductImage(productImage: ProductImage): ProductImage {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productImage)
        }.body()
    }

    suspend fun updateProductImage(productImageId: Int, productImage: ProductImage): ProductImage {
        return HttpClient.client.put("$BASE_URL${endPoint}/${productImageId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productImage)
        }.body()
    }

    suspend fun deleteProductImage(productImageId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${productImageId}")
            true
        } catch (e: Exception) {
            println("Error deleting productImage: ${e.message}")
            false
        }
    }

    suspend fun getProductImagesByProductId(currentPage: Int, product: Int): PaginatedResponse<ProductImage> {
        return HttpClient.client.get("$BASE_URL${endPoint}/product/id/${product}?size=${getPageSize()}&page=${currentPage}").body()
    }
}