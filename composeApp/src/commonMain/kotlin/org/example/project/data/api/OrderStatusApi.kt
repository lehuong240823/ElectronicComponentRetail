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
import org.example.project.domain.model.OrderStatus

class OrderStatusApi {
    val endPoint = "/api/order-statuss"

    suspend fun getAllOrderStatuss(currentPage: Int): PaginatedResponse<OrderStatus> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getOrderStatus(orderStatusId: Int): OrderStatus {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${orderStatusId}").body()
    }
    
    suspend fun createOrderStatus(orderStatus: OrderStatus): OrderStatus {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderStatus)
        }.body()
    }

    suspend fun updateOrderStatus(orderStatusId: Int, orderStatus: OrderStatus): OrderStatus {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${orderStatusId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderStatus)
        }.body()
    }

    suspend fun deleteOrderStatus(orderStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${orderStatusId}")
            true
        } catch (e: Exception) {
            println("Error deleting orderStatus: ${e.message}")
            false
        }
    }
}