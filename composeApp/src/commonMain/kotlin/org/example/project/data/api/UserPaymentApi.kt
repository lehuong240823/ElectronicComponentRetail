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
import org.example.project.domain.model.UserPayment

class UserPaymentApi {
    val endPoint = "/api/userPayments"

    suspend fun getAllUserPayments(): PaginatedResponse<UserPayment> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<UserPayment>>()
    }

    suspend fun getUserPayment(userPaymentId: Int): UserPayment {
        return Json.decodeFromString<UserPayment>(HttpClient.client.get(urlString = getUrl("${endPoint}/$userPaymentId")).body())
    }
    
    suspend fun createUserPayment(userPayment: UserPayment): UserPayment {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userPayment)
        }.body()
    }

    suspend fun updateUserPayment(userPaymentId: Int, userPayment: UserPayment): UserPayment {
        return HttpClient.client.put(getUrl("${endPoint}/$userPaymentId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userPayment)
        }.body()
    }

    suspend fun deleteUserPayment(userPaymentId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$userPaymentId"))
            true
        } catch (e: Exception) {
            println("Error deleting userPayment: ${e.message}")
            false
        }
    }
}