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
import org.example.project.domain.model.Account

class AccountApi {
    val endPoint = "/api/accounts"

    suspend fun createAccount(account: Account): Account {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(account)
        }.body()
    }

    suspend fun getAllAccounts(): List<Account> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Account>>()
    }

    suspend fun getAccount(accountId: Int): Account {
        return Json.decodeFromString<Account>(HttpClient.client.get(urlString = getUrl("$endPoint/$accountId")).body())
    }

    suspend fun updateAccount(accountId: Int, account: Account): Account {
        return HttpClient.client.put(getUrl("$endPoint/$accountId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(account)
        }.body()
    }

    suspend fun deleteAccount(accountId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("$endPoint/$accountId"))
            true
        } catch (e: Exception) {
            println("Error deleting account: ${e.message}")
            false
        }
    }

    suspend fun findAccountByEmail(email: String): Account {
        return Json.decodeFromString<List<Account>>(
            HttpClient.client.get(urlString = getUrl("$endPoint?email=$email")).body()
        ).first()
    }
}
