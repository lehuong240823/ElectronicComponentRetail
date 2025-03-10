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
import org.example.project.domain.model.AccountStatus

class AccountStatusApi {
    val endPoint = "/api/accountStatuss"

    suspend fun getAllAccountStatuss(): PaginatedResponse<AccountStatus> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<AccountStatus>>()
    }

    suspend fun getAccountStatus(accountStatusId: Int): AccountStatus {
        return Json.decodeFromString<AccountStatus>(HttpClient.client.get(urlString = getUrl("${endPoint}/$accountStatusId")).body())
    }
    
    suspend fun createAccountStatus(accountStatus: AccountStatus): AccountStatus {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountStatus)
        }.body()
    }

    suspend fun updateAccountStatus(accountStatusId: Int, accountStatus: AccountStatus): AccountStatus {
        return HttpClient.client.put(getUrl("${endPoint}/$accountStatusId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(accountStatus)
        }.body()
    }

    suspend fun deleteAccountStatus(accountStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$accountStatusId"))
            true
        } catch (e: Exception) {
            println("Error deleting accountStatus: ${e.message}")
            false
        }
    }
}