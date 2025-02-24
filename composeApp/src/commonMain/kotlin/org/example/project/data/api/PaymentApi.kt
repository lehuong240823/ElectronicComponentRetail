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
import org.example.project.domain.model.Payment

class PaymentApi {
    val endPoint = "/api/payments"

    suspend fun createPayment(payment: Payment): Payment {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(payment)
        }.body()
    }

    suspend fun getAllPayments(): List<Payment> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Payment>>()
    }

    suspend fun getPayment(paymentId: Int): Payment {
        return Json.decodeFromString<Payment>(HttpClient.client.get(urlString = getUrl("\$endPt/$paymentId")).body())
    }

    suspend fun updatePayment(paymentId: Int, payment: Payment): Payment {
        return HttpClient.client.put(getUrl("\$endPt/$paymentId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(payment)
        }.body()
    }

    suspend fun deletePayment(paymentId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("\$endPt/$paymentId"))
            true
        } catch (e: Exception) {
            println("Error deleting payment: errorMessage")
            false
        }
    }
}