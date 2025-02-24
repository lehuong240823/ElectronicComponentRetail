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
import org.example.project.domain.model.Product

class ProductApi {
    val endPoint = "/api/products"

    suspend fun createProduct(product: Product): Product {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(product)
        }.body()
    }

    suspend fun getAllProducts(): List<Product> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Product>>()
    }

    suspend fun getProduct(productId: Int): Product {
        return Json.decodeFromString<Product>(HttpClient.client.get(urlString = getUrl("\$endPt/$productId")).body())
    }

    suspend fun updateProduct(productId: Int, product: Product): Product {
        return HttpClient.client.put(getUrl("\$endPt/$productId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(product)
        }.body()
    }

    suspend fun deleteProduct(productId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("\$endPt/$productId"))
            true
        } catch (e: Exception) {
            println("Error deleting product: errorMessage")
            false
        }
    }
}