package org.example.project.data.repository

import org.example.project.domain.model.OrderStatus
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.OrderStatusApi

class OrderStatusRepository(private val orderStatusApi: OrderStatusApi) {

    suspend fun getAllOrderStatuss(currentPage: Int): PaginatedResponse<OrderStatus>? {
        return try {
            orderStatusApi.getAllOrderStatuses(currentPage)
        } catch (e: Exception) {
            println("Error fetching orderStatuss: ${e.message}")
            null
        }
    }
    
    suspend fun getOrderStatus(userId: Int): OrderStatus? {
        return try {
            orderStatusApi.getOrderStatus(userId)
        } catch (e: Exception) {
            println("Error fetching orderStatus: ${e.message}")
            null
        }
    }

    suspend fun createOrderStatus(orderStatus: OrderStatus): OrderStatus? {
        return try {
            orderStatusApi.createOrderStatus(orderStatus)
        } catch (e: Exception) {
            println("Error creating orderStatus: ${e.message}")
            null
        }
    }

    suspend fun updateOrderStatus(orderStatusId: Int, orderStatus: OrderStatus): OrderStatus? {
        return try {
            orderStatusApi.updateOrderStatus(orderStatusId, orderStatus)
        } catch (e: Exception) {
            println("Error updating orderStatus: ${e.message}")
            null
        }
    }

    suspend fun deleteOrderStatus(orderStatusId: Int): Boolean {
        return try {
            orderStatusApi.deleteOrderStatus(orderStatusId)
        } catch (e: Exception) {
            println("Error deleting orderStatus: ${e.message}")
            false
        }
    }
}