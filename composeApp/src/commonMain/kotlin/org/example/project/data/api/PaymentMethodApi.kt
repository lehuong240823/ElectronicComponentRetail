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
import org.example.project.domain.model.PaymentMethod

class PaymentMethodApi {
    val endPoint = "/api/payment-methods"

    suspend fun getAllPaymentMethods(currentPage: Int): PaginatedResponse<PaymentMethod> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getPaymentMethod(paymentMethodId: Int): PaymentMethod {
        return HttpClient.client.get("$BASE_URL${endPoint}/${paymentMethodId}").body()
    }
    
    suspend fun createPaymentMethod(paymentMethod: PaymentMethod): PaymentMethod {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(paymentMethod)
        }.body()
    }

    suspend fun updatePaymentMethod(paymentMethodId: Int, paymentMethod: PaymentMethod): PaymentMethod {
        return HttpClient.client.put("$BASE_URL${endPoint}/${paymentMethodId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(paymentMethod)
        }.body()
    }

    suspend fun deletePaymentMethod(paymentMethodId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${paymentMethodId}")
            true
        } catch (e: Exception) {
            println("Error deleting paymentMethod: ${e.message}")
            false
        }
    }
}