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
import org.example.project.domain.model.UserAddress

class UserAddressApi {
    val endPoint = "/api/userAddresss"

    suspend fun getAllUserAddresss(): PaginatedResponse<UserAddress> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<UserAddress>>()
    }

    suspend fun getUserAddress(userAddressId: Int): UserAddress {
        return Json.decodeFromString<UserAddress>(HttpClient.client.get(urlString = getUrl("${endPoint}/$userAddressId")).body())
    }
    
    suspend fun createUserAddress(userAddress: UserAddress): UserAddress {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userAddress)
        }.body()
    }

    suspend fun updateUserAddress(userAddressId: Int, userAddress: UserAddress): UserAddress {
        return HttpClient.client.put(getUrl("${endPoint}/$userAddressId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(userAddress)
        }.body()
    }

    suspend fun deleteUserAddress(userAddressId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$userAddressId"))
            true
        } catch (e: Exception) {
            println("Error deleting userAddress: ${e.message}")
            false
        }
    }
}