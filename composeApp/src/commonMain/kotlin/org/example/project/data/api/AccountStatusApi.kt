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
import org.example.project.domain.model.AccountStatus

class AccountStatusApi {
    val endPoint = "/api/account-statuses"

    suspend fun getAllAccountStatuss(currentPage: Int): PaginatedResponse<AccountStatus> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAccountStatus(accountStatusId: Int): AccountStatus {
        return HttpClient.client.get("$BASE_URL${endPoint}/${accountStatusId}").body()
    }
    
    suspend fun createAccountStatus(accountStatus: AccountStatus): AccountStatus {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountStatus)
        }.body()
    }

    suspend fun updateAccountStatus(accountStatusId: Int, accountStatus: AccountStatus): AccountStatus {
        return HttpClient.client.put("$BASE_URL${endPoint}/${accountStatusId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountStatus)
        }.body()
    }

    suspend fun deleteAccountStatus(accountStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${accountStatusId}")
            true
        } catch (e: Exception) {
            println("Error deleting accountStatus: ${e.message}")
            false
        }
    }
}