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
import org.example.project.domain.model.Administrator

class AdministratorApi {
    val endPoint = "/api/administrators"

    suspend fun getAllAdministrators(currentPage: Int): PaginatedResponse<Administrator> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAdministrator(administratorId: Int): Administrator {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${administratorId}").body()
    }
    
    suspend fun createAdministrator(administrator: Administrator): Administrator {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(administrator)
        }.body()
    }

    suspend fun updateAdministrator(administratorId: Int, administrator: Administrator): Administrator {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${administratorId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(administrator)
        }.body()
    }

    suspend fun deleteAdministrator(administratorId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${administratorId}")
            true
        } catch (e: Exception) {
            println("Error deleting administrator: ${e.message}")
            false
        }
    }
}