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
import org.example.project.domain.model.OrderItem

class OrderItemApi {
    val endPoint = "/api/order-items"

    suspend fun getAllOrderItems(currentPage: Int): PaginatedResponse<OrderItem> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getOrderItem(orderItemId: Int): OrderItem {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${orderItemId}").body()
    }
    
    suspend fun createOrderItem(orderItem: OrderItem): OrderItem {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderItem)
        }.body()
    }

    suspend fun updateOrderItem(orderItemId: Int, orderItem: OrderItem): OrderItem {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${orderItemId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderItem)
        }.body()
    }

    suspend fun deleteOrderItem(orderItemId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${orderItemId}")
            true
        } catch (e: Exception) {
            println("Error deleting orderItem: ${e.message}")
            false
        }
    }
}