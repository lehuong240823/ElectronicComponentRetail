package org.example.project.data.repository

import org.example.project.data.api.PaymentApi
import org.example.project.domain.model.Payment

class PaymentRepository(private val paymentApi: PaymentApi) {

    suspend fun getPayment(userId: Int): Payment? {
        return try {
            paymentApi.getPayment(userId)
        } catch (e: Exception) {
            println("Error fetching payment: \${e.message}")
            null
        }
    }

    suspend fun getAllPayments(): List<Payment>? {
        return try {
            paymentApi.getAllPayments()
        } catch (e: Exception) {
            println("Error fetching payments: \${e.message}")
            null
        }
    }

    suspend fun createPayment(payment: Payment): Payment? {
        return try {
            paymentApi.createPayment(payment)
        } catch (e: Exception) {
            println("Error creating payment: \${e.message}")
            null
        }
    }

    suspend fun updatePayment(paymentId: Int, payment: Payment): Payment? {
        return try {
            paymentApi.updatePayment(paymentId, payment)
        } catch (e: Exception) {
            println("Error updating payment: \${e.message}")
            null
        }
    }

    suspend fun deletePayment(paymentId: Int): Boolean {
        return try {
            paymentApi.deletePayment(paymentId)
        } catch (e: Exception) {
            println("Error deleting payment: \${e.message}")
            false
        }
    }
}
