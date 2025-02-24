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
import org.example.project.domain.model.Administrator

class AdministratorApi {
    val endPoint = "/api/administrators"

    suspend fun createAdministrator(administrator: Administrator): Administrator {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(administrator)
        }.body()
    }

    suspend fun getAllAdministrators(): List<Administrator> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Administrator>>()
    }

    suspend fun getAdministrator(administratorId: Int): Administrator {
        return Json.decodeFromString<Administrator>(
            HttpClient.client.get(urlString = getUrl("\$endPt/$administratorId")).body()
        )
    }

    suspend fun updateAdministrator(administratorId: Int, administrator: Administrator): Administrator {
        return HttpClient.client.put(getUrl("\$endPt/$administratorId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(administrator)
        }.body()
    }

    suspend fun deleteAdministrator(administratorId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("\$endPt/$administratorId"))
            true
        } catch (e: Exception) {
            println("Error deleting administrator: errorMessage")
            false
        }
    }
}