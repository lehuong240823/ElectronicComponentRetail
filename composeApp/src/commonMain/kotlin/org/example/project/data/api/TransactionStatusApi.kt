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
import org.example.project.domain.model.TransactionStatus

class TransactionStatusApi {
    val endPoint = "/api/transactionStatuss"

    suspend fun getAllTransactionStatuss(): PaginatedResponse<TransactionStatus> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<TransactionStatus>>()
    }

    suspend fun getTransactionStatus(transactionStatusId: Int): TransactionStatus {
        return Json.decodeFromString<TransactionStatus>(HttpClient.client.get(urlString = getUrl("${endPoint}/$transactionStatusId")).body())
    }
    
    suspend fun createTransactionStatus(transactionStatus: TransactionStatus): TransactionStatus {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transactionStatus)
        }.body()
    }

    suspend fun updateTransactionStatus(transactionStatusId: Int, transactionStatus: TransactionStatus): TransactionStatus {
        return HttpClient.client.put(getUrl("${endPoint}/$transactionStatusId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transactionStatus)
        }.body()
    }

    suspend fun deleteTransactionStatus(transactionStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$transactionStatusId"))
            true
        } catch (e: Exception) {
            println("Error deleting transactionStatus: ${e.message}")
            false
        }
    }
}