package org.example.project.data.repository

import org.example.project.domain.model.UserPayment
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.UserPaymentApi

class UserPaymentRepository(private val userPaymentApi: UserPaymentApi) {

    suspend fun getAllUserPayments(currentPage: Int): PaginatedResponse<UserPayment>? {
        return try {
            userPaymentApi.getAllUserPayments(currentPage)
        } catch (e: Exception) {
            println("Error fetching userPayments: ${e.message}")
            null
        }
    }
    
    suspend fun getUserPayment(userId: Int): UserPayment? {
        return try {
            userPaymentApi.getUserPayment(userId)
        } catch (e: Exception) {
            println("Error fetching userPayment: ${e.message}")
            null
        }
    }

    suspend fun createUserPayment(userPayment: UserPayment): UserPayment? {
        return try {
            userPaymentApi.createUserPayment(userPayment)
        } catch (e: Exception) {
            println("Error creating userPayment: ${e.message}")
            null
        }
    }

    suspend fun updateUserPayment(userPaymentId: Int, userPayment: UserPayment): UserPayment? {
        return try {
            userPaymentApi.updateUserPayment(userPaymentId, userPayment)
        } catch (e: Exception) {
            println("Error updating userPayment: ${e.message}")
            null
        }
    }

    suspend fun deleteUserPayment(userPaymentId: Int): Boolean {
        return try {
            userPaymentApi.deleteUserPayment(userPaymentId)
        } catch (e: Exception) {
            println("Error deleting userPayment: ${e.message}")
            false
        }
    }

    suspend fun getUserPaymentsByPaymentMethodId(currentPage: Int, paymentMethod: Byte): PaginatedResponse<UserPayment>? {
        return try {
            userPaymentApi.getUserPaymentsByPaymentMethodId(currentPage, paymentMethod)
        } catch (e: Exception) {
            println("Error fetching userPayments: ${e.message}")
            null
        }
    }

    suspend fun getUserPaymentsByUserId(currentPage: Int, user: Int): PaginatedResponse<UserPayment>? {
        return try {
            userPaymentApi.getUserPaymentsByUserId(currentPage, user)
        } catch (e: Exception) {
            println("Error fetching userPayments: ${e.message}")
            null
        }
    }
}