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
import org.example.project.domain.model.PaymentMethod

class PaymentMethodApi {
    val endPoint = "/api/paymentMethods"

    suspend fun getAllPaymentMethods(): PaginatedResponse<PaymentMethod> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<PaymentMethod>>()
    }

    suspend fun getPaymentMethod(paymentMethodId: Int): PaymentMethod {
        return Json.decodeFromString<PaymentMethod>(HttpClient.client.get(urlString = getUrl("${endPoint}/$paymentMethodId")).body())
    }
    
    suspend fun createPaymentMethod(paymentMethod: PaymentMethod): PaymentMethod {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(paymentMethod)
        }.body()
    }

    suspend fun updatePaymentMethod(paymentMethodId: Int, paymentMethod: PaymentMethod): PaymentMethod {
        return HttpClient.client.put(getUrl("${endPoint}/$paymentMethodId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(paymentMethod)
        }.body()
    }

    suspend fun deletePaymentMethod(paymentMethodId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$paymentMethodId"))
            true
        } catch (e: Exception) {
            println("Error deleting paymentMethod: ${e.message}")
            false
        }
    }
}