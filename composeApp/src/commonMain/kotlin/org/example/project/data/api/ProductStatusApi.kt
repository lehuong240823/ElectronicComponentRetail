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
import org.example.project.core.BASE_URL
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.ProductStatus

class ProductStatusApi {
    val endPoint = "/api/product-statuss"

    suspend fun getAllProductStatuss(currentPage: Int): PaginatedResponse<ProductStatus> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getProductStatus(productStatusId: Int): ProductStatus {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${productStatusId}").body()
    }
    
    suspend fun createProductStatus(productStatus: ProductStatus): ProductStatus {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productStatus)
        }.body()
    }

    suspend fun updateProductStatus(productStatusId: Int, productStatus: ProductStatus): ProductStatus {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${productStatusId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productStatus)
        }.body()
    }

    suspend fun deleteProductStatus(productStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${productStatusId}")
            true
        } catch (e: Exception) {
            println("Error deleting productStatus: ${e.message}")
            false
        }
    }
}