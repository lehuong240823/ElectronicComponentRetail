package org.example.project.data.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.project.BASE_URL
import org.example.project.core.HttpClient
import org.example.project.domain.model.Administrator
import org.example.project.domain.model.PaginatedResponse
import org.example.project.getPageSize

class AdministratorApi {
    val endPoint = "/api/administrators"

    suspend fun getAllAdministrators(currentPage: Int): PaginatedResponse<Administrator> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAdministrator(administratorId: Int): Administrator {
        return HttpClient.client.get("$BASE_URL${endPoint}/${administratorId}").body()
    }
    
    suspend fun createAdministrator(administrator: Administrator): Administrator {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(administrator)
        }.body()
    }

    suspend fun updateAdministrator(administratorId: Int, administrator: Administrator): Administrator {
        return HttpClient.client.put("$BASE_URL${endPoint}/${administratorId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(administrator)
        }.body()
    }

    suspend fun deleteAdministrator(administratorId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${administratorId}")
            true
        } catch (e: Exception) {
            println("Error deleting administrator: ${e.message}")
            false
        }
    }

    suspend fun getAdministratorsByJobPositionId(currentPage: Int, jobPosition: Byte): PaginatedResponse<Administrator> {
        return HttpClient.client.get("$BASE_URL${endPoint}/job-position/id/${jobPosition}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAdministratorsByAccessLevelId(currentPage: Int, accessLevel: Byte): PaginatedResponse<Administrator> {
        return HttpClient.client.get("$BASE_URL${endPoint}/access-level/id/${accessLevel}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAdministratorByAccountId(account: Int): Administrator? {
        return HttpClient.client.get("$BASE_URL${endPoint}/account/id/${account}").body()
    }
}