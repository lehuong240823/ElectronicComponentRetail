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
import org.example.project.domain.model.User

class UserApi {
    val endPoint = "/api/users"

    suspend fun getAllUsers(currentPage: Int): PaginatedResponse<User> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getUser(userId: Int): User {
        return HttpClient.client.get("$BASE_URL${endPoint}/${userId}").body()
    }
    
    suspend fun createUser(user: User): User {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun updateUser(userId: Int, user: User): User {
        return HttpClient.client.put("$BASE_URL${endPoint}/${userId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun deleteUser(userId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${userId}")
            true
        } catch (e: Exception) {
            println("Error deleting user: ${e.message}")
            false
        }
    }

    suspend fun getUsersByAccountId(currentPage: Int, account: Int): PaginatedResponse<User> {
        return HttpClient.client.get("$BASE_URL${endPoint}/account/id/${account}?size=${getPageSize()}&page=${currentPage}").body()
    }
}