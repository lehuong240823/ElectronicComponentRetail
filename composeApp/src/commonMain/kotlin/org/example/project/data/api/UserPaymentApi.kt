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
import org.example.project.core.BASE_URL
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.UserPayment

class UserPaymentApi {
    val endPoint = "/api/user-payments"

    suspend fun getAllUserPayments(currentPage: Int): PaginatedResponse<UserPayment> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getUserPayment(userPaymentId: Int): UserPayment {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${userPaymentId}").body()
    }
    
    suspend fun createUserPayment(userPayment: UserPayment): UserPayment {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userPayment)
        }.body()
    }

    suspend fun updateUserPayment(userPaymentId: Int, userPayment: UserPayment): UserPayment {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${userPaymentId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userPayment)
        }.body()
    }

    suspend fun deleteUserPayment(userPaymentId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${userPaymentId}")
            true
        } catch (e: Exception) {
            println("Error deleting userPayment: ${e.message}")
            false
        }
    }
}