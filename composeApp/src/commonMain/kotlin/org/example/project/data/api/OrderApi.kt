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
import org.example.project.domain.model.Order

class OrderApi {
    val endPoint = "/api/orders"

    suspend fun getAllOrders(currentPage: Int): PaginatedResponse<Order> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getOrder(orderId: Int): Order {
        return HttpClient.client.get("$BASE_URL${endPoint}/${orderId}").body()
    }
    
    suspend fun createOrder(order: Order, cartItemIds: List<Int>): Order {
        return HttpClient.client.post("$BASE_URL${endPoint}?cartItemIds=${cartItemIds.joinToString(",")}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(order)
        }.body()
    }

    suspend fun updateOrder(orderId: Int, order: Order): Order {
        return HttpClient.client.put("$BASE_URL${endPoint}/${orderId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(order)
        }.body()
    }

    suspend fun deleteOrder(orderId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${orderId}")
            true
        } catch (e: Exception) {
            println("Error deleting order: ${e.message}")
            false
        }
    }

    suspend fun getOrdersByOrderStatusId(currentPage: Int, orderStatus: Byte): PaginatedResponse<Order> {
        return HttpClient.client.get("$BASE_URL${endPoint}/order-status/id/${orderStatus}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getOrdersByUserId(currentPage: Int, user: Int): PaginatedResponse<Order> {
        return HttpClient.client.get("$BASE_URL${endPoint}/user/id/${user}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getOrdersByVoucherId(currentPage: Int, voucher: Int): PaginatedResponse<Order> {
        return HttpClient.client.get("$BASE_URL${endPoint}/voucher/id/${voucher}?size=${getPageSize()}&page=${currentPage}").body()
    }
}