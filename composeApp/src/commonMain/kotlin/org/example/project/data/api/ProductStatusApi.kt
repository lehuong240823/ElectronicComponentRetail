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
import org.example.project.domain.model.ProductStatus

class ProductStatusApi {
    val endPoint = "/api/productStatuss"

    suspend fun getAllProductStatuss(): PaginatedResponse<ProductStatus> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<ProductStatus>>()
    }

    suspend fun getProductStatus(productStatusId: Int): ProductStatus {
        return Json.decodeFromString<ProductStatus>(HttpClient.client.get(urlString = getUrl("${endPoint}/$productStatusId")).body())
    }
    
    suspend fun createProductStatus(productStatus: ProductStatus): ProductStatus {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productStatus)
        }.body()
    }

    suspend fun updateProductStatus(productStatusId: Int, productStatus: ProductStatus): ProductStatus {
        return HttpClient.client.put(getUrl("${endPoint}/$productStatusId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(productStatus)
        }.body()
    }

    suspend fun deleteProductStatus(productStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$productStatusId"))
            true
        } catch (e: Exception) {
            println("Error deleting productStatus: ${e.message}")
            false
        }
    }
}