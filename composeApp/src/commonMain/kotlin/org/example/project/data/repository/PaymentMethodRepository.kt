package org.example.project.data.repository

import org.example.project.domain.model.PaymentMethod
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.PaymentMethodApi

class PaymentMethodRepository(private val paymentMethodApi: PaymentMethodApi) {

    suspend fun getAllPaymentMethods(): PaginatedResponse<PaymentMethod>? {
        return try {
            paymentMethodApi.getAllPaymentMethods()
        } catch (e: Exception) {
            println("Error fetching paymentMethods: ${e.message}")
            null
        }
    }
    
    suspend fun getPaymentMethod(userId: Int): PaymentMethod? {
        return try {
            paymentMethodApi.getPaymentMethod(userId)
        } catch (e: Exception) {
            println("Error fetching paymentMethod: ${e.message}")
            null
        }
    }

    suspend fun createPaymentMethod(paymentMethod: PaymentMethod): PaymentMethod? {
        return try {
            paymentMethodApi.createPaymentMethod(paymentMethod)
        } catch (e: Exception) {
            println("Error creating paymentMethod: ${e.message}")
            null
        }
    }

    suspend fun updatePaymentMethod(paymentMethodId: Int, paymentMethod: PaymentMethod): PaymentMethod? {
        return try {
            paymentMethodApi.updatePaymentMethod(paymentMethodId, paymentMethod)
        } catch (e: Exception) {
            println("Error updating paymentMethod: ${e.message}")
            null
        }
    }

    suspend fun deletePaymentMethod(paymentMethodId: Int): Boolean {
        return try {
            paymentMethodApi.deletePaymentMethod(paymentMethodId)
        } catch (e: Exception) {
            println("Error deleting paymentMethod: ${e.message}")
            false
        }
    }
}
