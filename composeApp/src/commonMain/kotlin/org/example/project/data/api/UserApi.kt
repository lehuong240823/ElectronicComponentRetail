package org.example.project.data.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.project.BASE_URL
import org.example.project.core.HttpClient
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.User
import org.example.project.getPageSize

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

    suspend fun getUserByAccountId(account: Int): User? {
        return HttpClient.client.get("$BASE_URL${endPoint}/account/id/${account}").body()
    }
}