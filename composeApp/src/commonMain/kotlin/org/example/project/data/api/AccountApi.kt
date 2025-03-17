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
import org.example.project.domain.model.Account

class AccountApi {
    val endPoint = "/api/accounts"

    suspend fun getAllAccounts(currentPage: Int): PaginatedResponse<Account> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getAccount(accountId: Int): Account {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${accountId}").body()
    }
    
    suspend fun createAccount(account: Account): Account {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(account)
        }.body()
    }

    suspend fun updateAccount(accountId: Int, account: Account): Account {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${accountId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(account)
        }.body()
    }

    suspend fun deleteAccount(accountId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${accountId}")
            true
        } catch (e: Exception) {
            println("Error deleting account: ${e.message}")
            false
        }
    }

    suspend fun getAccountByEmail(email: String): Account {
        return HttpClient.client.get("${BASE_URL}${endPoint}/email/$email").body()
    }
}