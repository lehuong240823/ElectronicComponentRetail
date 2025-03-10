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
import org.example.project.domain.model.AccessLevel

class AccessLevelApi {
    val endPoint = "/api/accessLevels"

    suspend fun getAllAccessLevels(): PaginatedResponse<AccessLevel> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<AccessLevel>>()
    }

    suspend fun getAccessLevel(accessLevelId: Int): AccessLevel {
        return Json.decodeFromString<AccessLevel>(HttpClient.client.get(urlString = getUrl("${endPoint}/$accessLevelId")).body())
    }
    
    suspend fun createAccessLevel(accessLevel: AccessLevel): AccessLevel {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accessLevel)
        }.body()
    }

    suspend fun updateAccessLevel(accessLevelId: Int, accessLevel: AccessLevel): AccessLevel {
        return HttpClient.client.put(getUrl("${endPoint}/$accessLevelId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accessLevel)
        }.body()
    }

    suspend fun deleteAccessLevel(accessLevelId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$accessLevelId"))
            true
        } catch (e: Exception) {
            println("Error deleting accessLevel: ${e.message}")
            false
        }
    }
}