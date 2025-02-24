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
import org.example.project.domain.model.Transaction

class TransactionApi {
    val endPoint = "/api/transactions"

    suspend fun createTransaction(transaction: Transaction): Transaction {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transaction)
        }.body()
    }

    suspend fun getAllTransactions(): List<Transaction> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Transaction>>()
    }

    suspend fun getTransaction(transactionId: Int): Transaction {
        return Json.decodeFromString<Transaction>(
            HttpClient.client.get(urlString = getUrl("\$endPt/$transactionId")).body()
        )
    }

    suspend fun updateTransaction(transactionId: Int, transaction: Transaction): Transaction {
        return HttpClient.client.put(getUrl("\$endPt/$transactionId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transaction)
        }.body()
    }

    suspend fun deleteTransaction(transactionId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("\$endPt/$transactionId"))
            true
        } catch (e: Exception) {
            println("Error deleting transaction: errorMessage")
            false
        }
    }
}