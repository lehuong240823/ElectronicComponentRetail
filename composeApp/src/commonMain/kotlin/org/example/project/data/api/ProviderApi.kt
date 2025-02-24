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
import org.example.project.domain.model.Provider

class ProviderApi {
    val endPoint = "/api/providers"

    suspend fun createProvider(provider: Provider): Provider {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(provider)
        }.body()
    }

    suspend fun getAllProviders(): List<Provider> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Provider>>()
    }

    suspend fun getProvider(providerId: Int): Provider {
        return Json.decodeFromString<Provider>(HttpClient.client.get(urlString = getUrl("\$endPt/$providerId")).body())
    }

    suspend fun updateProvider(providerId: Int, provider: Provider): Provider {
        return HttpClient.client.put(getUrl("\$endPt/$providerId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(provider)
        }.body()
    }

    suspend fun deleteProvider(providerId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("\$endPt/$providerId"))
            true
        } catch (e: Exception) {
            println("Error deleting provider: errorMessage")
            false
        }
    }
}