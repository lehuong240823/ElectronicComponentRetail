package org.example.project.data.repository

import org.example.project.domain.model.Order
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.OrderApi

class OrderRepository(private val orderApi: OrderApi) {

    suspend fun getAllOrders(currentPage: Int): PaginatedResponse<Order>? {
        return try {
            orderApi.getAllOrders(currentPage)
        } catch (e: Exception) {
            println("Error fetching orders: ${e.message}")
            null
        }
    }
    
    suspend fun getOrder(userId: Int): Order? {
        return try {
            orderApi.getOrder(userId)
        } catch (e: Exception) {
            println("Error fetching order: ${e.message}")
            null
        }
    }

    suspend fun createOrder(order: Order): Order? {
        return try {
            orderApi.createOrder(order)
        } catch (e: Exception) {
            println("Error creating order: ${e.message}")
            null
        }
    }

    suspend fun updateOrder(orderId: Int, order: Order): Order? {
        return try {
            orderApi.updateOrder(orderId, order)
        } catch (e: Exception) {
            println("Error updating order: ${e.message}")
            null
        }
    }

    suspend fun deleteOrder(orderId: Int): Boolean {
        return try {
            orderApi.deleteOrder(orderId)
        } catch (e: Exception) {
            println("Error deleting order: ${e.message}")
            false
        }
    }
}