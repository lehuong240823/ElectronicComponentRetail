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
import org.example.project.domain.model.Provider

class ProviderApi {
    val endPoint = "/api/providers"

    suspend fun getAllProviders(currentPage: Int): PaginatedResponse<Provider> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getProvider(providerId: Int): Provider {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${providerId}").body()
    }
    
    suspend fun createProvider(provider: Provider): Provider {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(provider)
        }.body()
    }

    suspend fun updateProvider(providerId: Int, provider: Provider): Provider {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${providerId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(provider)
        }.body()
    }

    suspend fun deleteProvider(providerId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${providerId}")
            true
        } catch (e: Exception) {
            println("Error deleting provider: ${e.message}")
            false
        }
    }
}