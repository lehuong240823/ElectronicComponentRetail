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
import org.example.project.domain.model.AccountRole

class AccountRoleApi {
    val endPoint = "/api/accountRoles"

    suspend fun getAllAccountRoles(currentPage: Int): PaginatedResponse<AccountRole> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAccountRole(accountRoleId: Int): AccountRole {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${accountRoleId}").body()
    }
    
    suspend fun createAccountRole(accountRole: AccountRole): AccountRole {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountRole)
        }.body()
    }

    suspend fun updateAccountRole(accountRoleId: Int, accountRole: AccountRole): AccountRole {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${accountRoleId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountRole)
        }.body()
    }

    suspend fun deleteAccountRole(accountRoleId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${accountRoleId}")
            true
        } catch (e: Exception) {
            println("Error deleting accountRole: ${e.message}")
            false
        }
    }
}