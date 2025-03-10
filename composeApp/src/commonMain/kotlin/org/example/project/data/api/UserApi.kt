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
import org.example.project.domain.model.User

class UserApi {
    val endPoint = "/api/users"

    suspend fun getAllUsers(): PaginatedResponse<User> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<User>>()
    }

    suspend fun getUser(userId: Int): User {
        return Json.decodeFromString<User>(HttpClient.client.get(urlString = getUrl("${endPoint}/$userId")).body())
    }
    
    suspend fun createUser(user: User): User {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun updateUser(userId: Int, user: User): User {
        return HttpClient.client.put(getUrl("${endPoint}/$userId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun deleteUser(userId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$userId"))
            true
        } catch (e: Exception) {
            println("Error deleting user: ${e.message}")
            false
        }
    }
}