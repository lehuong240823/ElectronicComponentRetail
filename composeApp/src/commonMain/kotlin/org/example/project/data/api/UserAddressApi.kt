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
import org.example.project.domain.model.UserAddress

class UserAddressApi {
    val endPoint = "/api/user-addresss"

    suspend fun getAllUserAddresss(currentPage: Int): PaginatedResponse<UserAddress> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getUserAddress(userAddressId: Int): UserAddress {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${userAddressId}").body()
    }
    
    suspend fun createUserAddress(userAddress: UserAddress): UserAddress {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userAddress)
        }.body()
    }

    suspend fun updateUserAddress(userAddressId: Int, userAddress: UserAddress): UserAddress {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${userAddressId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userAddress)
        }.body()
    }

    suspend fun deleteUserAddress(userAddressId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${userAddressId}")
            true
        } catch (e: Exception) {
            println("Error deleting userAddress: ${e.message}")
            false
        }
    }
}