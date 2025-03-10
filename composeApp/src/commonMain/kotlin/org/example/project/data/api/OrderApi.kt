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
import org.example.project.domain.model.Order

class OrderApi {
    val endPoint = "/api/orders"

    suspend fun getAllOrders(): PaginatedResponse<Order> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<Order>>()
    }

    suspend fun getOrder(orderId: Int): Order {
        return Json.decodeFromString<Order>(HttpClient.client.get(urlString = getUrl("${endPoint}/$orderId")).body())
    }
    
    suspend fun createOrder(order: Order): Order {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(order)
        }.body()
    }

    suspend fun updateOrder(orderId: Int, order: Order): Order {
        return HttpClient.client.put(getUrl("${endPoint}/$orderId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(order)
        }.body()
    }

    suspend fun deleteOrder(orderId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$orderId"))
            true
        } catch (e: Exception) {
            println("Error deleting order: ${e.message}")
            false
        }
    }
}