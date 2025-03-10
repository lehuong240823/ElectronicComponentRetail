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
import org.example.project.domain.model.OrderStatus

class OrderStatusApi {
    val endPoint = "/api/orderStatuss"

    suspend fun getAllOrderStatuss(): PaginatedResponse<OrderStatus> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<OrderStatus>>()
    }

    suspend fun getOrderStatus(orderStatusId: Int): OrderStatus {
        return Json.decodeFromString<OrderStatus>(HttpClient.client.get(urlString = getUrl("${endPoint}/$orderStatusId")).body())
    }
    
    suspend fun createOrderStatus(orderStatus: OrderStatus): OrderStatus {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderStatus)
        }.body()
    }

    suspend fun updateOrderStatus(orderStatusId: Int, orderStatus: OrderStatus): OrderStatus {
        return HttpClient.client.put(getUrl("${endPoint}/$orderStatusId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(orderStatus)
        }.body()
    }

    suspend fun deleteOrderStatus(orderStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$orderStatusId"))
            true
        } catch (e: Exception) {
            println("Error deleting orderStatus: ${e.message}")
            false
        }
    }
}