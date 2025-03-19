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
import org.example.project.domain.model.AccessLevel

class AccessLevelApi {
    val endPoint = "/api/access-levels"

    suspend fun getAllAccessLevels(currentPage: Int): PaginatedResponse<AccessLevel> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAccessLevel(accessLevelId: Int): AccessLevel {
        return HttpClient.client.get("$BASE_URL${endPoint}/${accessLevelId}").body()
    }
    
    suspend fun createAccessLevel(accessLevel: AccessLevel): AccessLevel {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accessLevel)
        }.body()
    }

    suspend fun updateAccessLevel(accessLevelId: Int, accessLevel: AccessLevel): AccessLevel {
        return HttpClient.client.put("$BASE_URL${endPoint}/${accessLevelId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accessLevel)
        }.body()
    }

    suspend fun deleteAccessLevel(accessLevelId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${accessLevelId}")
            true
        } catch (e: Exception) {
            println("Error deleting accessLevel: ${e.message}")
            false
        }
    }
}