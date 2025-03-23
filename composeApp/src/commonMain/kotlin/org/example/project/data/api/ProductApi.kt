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
import org.example.project.domain.model.Product

class ProductApi {
    val endPoint = "/api/products"

    suspend fun getAllProducts(currentPage: Int): PaginatedResponse<Product> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getProduct(productId: Int): Product {
        return HttpClient.client.get("$BASE_URL${endPoint}/${productId}").body()
    }
    
    suspend fun createProduct(product: Product): Product {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(product)
        }.body()
    }

    suspend fun updateProduct(productId: Int, product: Product): Product {
        return HttpClient.client.put("$BASE_URL${endPoint}/${productId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(product)
        }.body()
    }

    suspend fun deleteProduct(productId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${productId}")
            true
        } catch (e: Exception) {
            println("Error deleting product: ${e.message}")
            false
        }
    }

    suspend fun getProductsByProductStatusId(currentPage: Int, productStatus: Byte): PaginatedResponse<Product> {
        return HttpClient.client.get("$BASE_URL${endPoint}/product-status/id/${productStatus}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getProductsByCategoryId(currentPage: Int, category: Int): PaginatedResponse<Product> {
        return HttpClient.client.get("$BASE_URL${endPoint}/category/id/${category}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getProductsByProviderId(currentPage: Int, provider: Int): PaginatedResponse<Product> {
        return HttpClient.client.get("$BASE_URL${endPoint}/provider/id/${provider}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun findProductsByNameContainingIgnoreCase(currentPage: Int, name: String): PaginatedResponse<Product> {
        return HttpClient.client.get("$BASE_URL${endPoint}/name?name=${name}&size=${getPageSize()}&page=${currentPage}").body()
    }
}