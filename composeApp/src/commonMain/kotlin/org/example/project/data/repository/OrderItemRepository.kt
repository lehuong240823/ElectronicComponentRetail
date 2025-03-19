package org.example.project.data.repository

import org.example.project.domain.model.OrderItem
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.OrderItemApi

class OrderItemRepository(private val orderItemApi: OrderItemApi) {

    suspend fun getAllOrderItems(currentPage: Int): PaginatedResponse<OrderItem>? {
        return try {
            orderItemApi.getAllOrderItems(currentPage)
        } catch (e: Exception) {
            println("Error fetching orderItems: ${e.message}")
            null
        }
    }
    
    suspend fun getOrderItem(userId: Int): OrderItem? {
        return try {
            orderItemApi.getOrderItem(userId)
        } catch (e: Exception) {
            println("Error fetching orderItem: ${e.message}")
            null
        }
    }

    suspend fun createOrderItem(orderItem: OrderItem): OrderItem? {
        return try {
            orderItemApi.createOrderItem(orderItem)
        } catch (e: Exception) {
            println("Error creating orderItem: ${e.message}")
            null
        }
    }

    suspend fun updateOrderItem(orderItemId: Int, orderItem: OrderItem): OrderItem? {
        return try {
            orderItemApi.updateOrderItem(orderItemId, orderItem)
        } catch (e: Exception) {
            println("Error updating orderItem: ${e.message}")
            null
        }
    }

    suspend fun deleteOrderItem(orderItemId: Int): Boolean {
        return try {
            orderItemApi.deleteOrderItem(orderItemId)
        } catch (e: Exception) {
            println("Error deleting orderItem: ${e.message}")
            false
        }
    }

    suspend fun getOrderItemsByOrderId(currentPage: Int, order: Int): PaginatedResponse<OrderItem>? {
        return try {
            orderItemApi.getOrderItemsByOrderId(currentPage, order)
        } catch (e: Exception) {
            println("Error fetching orderItems: ${e.message}")
            null
        }
    }

    suspend fun getOrderItemsByProductId(currentPage: Int, product: Int): PaginatedResponse<OrderItem>? {
        return try {
            orderItemApi.getOrderItemsByProductId(currentPage, product)
        } catch (e: Exception) {
            println("Error fetching orderItems: ${e.message}")
            null
        }
    }
}