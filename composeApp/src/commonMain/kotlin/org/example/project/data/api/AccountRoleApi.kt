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
import org.example.project.domain.model.AccountRole

class AccountRoleApi {
    val endPoint = "/api/accountRoles"

    suspend fun getAllAccountRoles(): PaginatedResponse<AccountRole> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<AccountRole>>()
    }

    suspend fun getAccountRole(accountRoleId: Int): AccountRole {
        return Json.decodeFromString<AccountRole>(HttpClient.client.get(urlString = getUrl("${endPoint}/$accountRoleId")).body())
    }
    
    suspend fun createAccountRole(accountRole: AccountRole): AccountRole {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountRole)
        }.body()
    }

    suspend fun updateAccountRole(accountRoleId: Int, accountRole: AccountRole): AccountRole {
        return HttpClient.client.put(getUrl("${endPoint}/$accountRoleId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountRole)
        }.body()
    }

    suspend fun deleteAccountRole(accountRoleId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$accountRoleId"))
            true
        } catch (e: Exception) {
            println("Error deleting accountRole: ${e.message}")
            false
        }
    }
}