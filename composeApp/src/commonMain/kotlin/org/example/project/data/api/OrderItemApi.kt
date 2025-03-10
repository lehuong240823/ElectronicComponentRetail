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
import org.example.project.domain.model.OrderItem

class OrderItemApi {
    val endPoint = "/api/orderItems"

    suspend fun getAllOrderItems(): PaginatedResponse<OrderItem> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<OrderItem>>()
    }

    suspend fun getOrderItem(orderItemId: Int): OrderItem {
        return Json.decodeFromString<OrderItem>(HttpClient.client.get(urlString = getUrl("${endPoint}/$orderItemId")).body())
    }
    
    suspend fun createOrderItem(orderItem: OrderItem): OrderItem {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderItem)
        }.body()
    }

    suspend fun updateOrderItem(orderItemId: Int, orderItem: OrderItem): OrderItem {
        return HttpClient.client.put(getUrl("${endPoint}/$orderItemId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderItem)
        }.body()
    }

    suspend fun deleteOrderItem(orderItemId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$orderItemId"))
            true
        } catch (e: Exception) {
            println("Error deleting orderItem: ${e.message}")
            false
        }
    }
}